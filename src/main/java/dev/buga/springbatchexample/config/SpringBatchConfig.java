package dev.buga.springbatchexample.config;

import dev.buga.springbatchexample.entity.Dwelling;
import dev.buga.springbatchexample.repository.DateEntityRepository;
import dev.buga.springbatchexample.repository.DwellingInfoRepository;
import dev.buga.springbatchexample.repository.DwellingsRepository;
import dev.buga.springbatchexample.repository.SA2EntityRepository;
import dev.buga.springbatchexample.utility.DwellingItemWriter;
import dev.buga.springbatchexample.utility.DwellingProcessor;
import dev.buga.springbatchexample.utility.DwellingValidator;
import dev.buga.springbatchexample.utility.FileMoveListener;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PassThroughFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
@AllArgsConstructor
public class SpringBatchConfig {

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    private DwellingsRepository dwellingsRepository;

    @Bean
    public FlatFileItemReader<FieldSet> reader() {
        FlatFileItemReader<FieldSet> itemReader = new FlatFileItemReader<>();

        itemReader.setResource(new FileSystemResource("src/main/resources/dwellings4.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    private LineMapper<FieldSet> lineMapper() {
        DefaultLineMapper<FieldSet> lineMapper = new DefaultLineMapper<>();

        // extracts the value from the csv file
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("month", "SA2_code", "SA2_name", "territorial_authority", "total_dwelling_units", "houses", "apartments", "retirement_village_units", "townhouses_flats_units_other");

        // maps the value to the target class
        FieldSetMapper<FieldSet> fieldSetMapper = new PassThroughFieldSetMapper();

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public DwellingProcessor processor(DateEntityRepository dateEntityRepository, DwellingInfoRepository dwellingInfoRepository, SA2EntityRepository sa2EntityRepository) {
        return new DwellingProcessor(validator(), dwellingInfoRepository, dateEntityRepository, sa2EntityRepository);
    }

    @Bean
    public RepositoryItemWriter<Dwelling> writer() {
        RepositoryItemWriter<Dwelling> writer = new RepositoryItemWriter<>();

        writer.setRepository(dwellingsRepository);
        writer.setMethodName("save");

        return writer;
    }

    @Bean
    public Step step1(DwellingProcessor processor, DwellingItemWriter dwellingItemWriter) {
        return new StepBuilder("csv-step", jobRepository)
                .<FieldSet, List<Object>>chunk(50, transactionManager)
                .reader(reader())
                .processor(processor)
                .writer(dwellingItemWriter)
                .faultTolerant()
                .skipPolicy((throwable, skipCount) -> {
                    if (throwable instanceof ValidationException) {
                        System.out.println(throwable);
                        return true;
                    }
                    return false;
                })
                .skipLimit(10)
                .build();
    }

    @Bean
    public Job runJob(FileMoveListener listener, Step step1) {
        return new JobBuilder("importDwellings", jobRepository)
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public DwellingValidator validator() {
        return new DwellingValidator();
    }

}

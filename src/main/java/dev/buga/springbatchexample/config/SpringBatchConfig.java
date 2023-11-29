package dev.buga.springbatchexample.config;

import dev.buga.springbatchexample.entity.Dwelling;
import dev.buga.springbatchexample.dto.DwellingDTO;
import dev.buga.springbatchexample.repository.DwellingsRepository;
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
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class SpringBatchConfig {

    private JobRepository jobRepository;
    private PlatformTransactionManager transactionManager;
    private DwellingsRepository dwellingsRepository;

    @Bean
    public FlatFileItemReader<DwellingDTO> reader() {
        FlatFileItemReader<DwellingDTO> itemReader = new FlatFileItemReader<>();

        itemReader.setResource(new FileSystemResource("src/main/resources/dwellings4.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    private LineMapper<DwellingDTO> lineMapper() {
        DefaultLineMapper<DwellingDTO> lineMapper = new DefaultLineMapper<>();

        // extracts the value from the csv file
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("date", "SA2Code", "SA2Name", "authority", "totalDwellings", "houses", "apartments", "retirementVillas", "townhouses");

        // maps the value to the target class
        BeanWrapperFieldSetMapper<DwellingDTO> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(DwellingDTO.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public DwellingProcessor processor() {
        return new DwellingProcessor(validator());
    }

    @Bean
    public RepositoryItemWriter<Dwelling> writer() {
        RepositoryItemWriter<Dwelling> writer = new RepositoryItemWriter<>();

        writer.setRepository(dwellingsRepository);
        writer.setMethodName("save");

        return writer;
    }

    @Bean
    public Step step1() {
        return new StepBuilder("csv-step", jobRepository)
                .<DwellingDTO, Dwelling>chunk(50, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
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
    public Job runJob(FileMoveListener listener) {
        return new JobBuilder("importDwellings", jobRepository)
                .listener(listener)
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public DwellingValidator validator() {
        return new DwellingValidator();
    }

}

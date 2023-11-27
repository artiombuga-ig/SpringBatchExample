package dev.buga.springbatchexample.config;

import dev.buga.springbatchexample.entity.Dwelling;
import dev.buga.springbatchexample.repository.DwellingsRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
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
    public FlatFileItemReader<Dwelling> reader() {
        FlatFileItemReader<Dwelling> itemReader = new FlatFileItemReader<>();

        itemReader.setResource(new FileSystemResource("src/main/resources/dwellings2.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    private LineMapper<Dwelling> lineMapper() {
        DefaultLineMapper<Dwelling> lineMapper = new DefaultLineMapper<>();

        // extracts the value from the csv file
        DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
        delimitedLineTokenizer.setDelimiter(",");
        delimitedLineTokenizer.setStrict(false);
        delimitedLineTokenizer.setNames("date", "SA2_code", "SA2_name", "authority", "total_dwellings", "houses", "apartments", "retirement_villas", "townhouses");

        // maps the value to the target class
        BeanWrapperFieldSetMapper<Dwelling> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Dwelling.class);

        lineMapper.setLineTokenizer(delimitedLineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    public DwellingProcessor processor() {
        return new DwellingProcessor();
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
                .<Dwelling, Dwelling>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder("importDwellings", jobRepository)
                .flow(step1())
                .end()
                .build();
    }

}

package com.example.demo.configuration;

import com.example.demo.batch.CassandraBatchItemReader;
import com.example.demo.batch.CassandraBatchItemWriter;
import com.example.demo.batch.EmployeeItemProcessor;
import com.example.demo.model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.example.demo")
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<Employee> reader() {
        final CassandraBatchItemReader<Employee> reader = new CassandraBatchItemReader<Employee>(Employee.class);
        return reader;
    }

    @Bean
    public ItemProcessor<Employee, Employee> processor() {
        return new EmployeeItemProcessor();
    }

    @Bean
    public ItemWriter<Employee> writer() {
        final CassandraBatchItemWriter<Employee> writer = new CassandraBatchItemWriter<Employee>(Employee.class);
        return writer;
    }

    @Bean
    public Job newSchemaJob(final JobBuilderFactory jobs, final Step s1, final JobExecutionListener listener) {
        return jobs.get("newSchemaJob").incrementer(new RunIdIncrementer()).listener(listener).flow(s1).end().build();
    }

    @Bean
    public Step stepOne(final StepBuilderFactory stepBuilderFactory, final ItemReader<Employee> reader,
                        final ItemWriter<Employee> writer, final ItemProcessor<Employee, Employee> processor) {
        return stepBuilderFactory.get("stepOne").<Employee, Employee>chunk(100).reader(reader).processor(processor)
                                 .writer(writer).build();
    }

//    @Bean
//    public JdbcTemplate jdbcTemplate(final DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
}

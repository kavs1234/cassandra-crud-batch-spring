package com.example.demo.batch;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final CassandraTemplate cassandraTemplate;

    @Autowired
    private  EmployeeRepository employeeRepository;

    @Autowired
    public JobCompletionNotificationListener(final CassandraTemplate cassandraTemplate) {
        this.cassandraTemplate = cassandraTemplate;
    }

    @Override
    public void afterJob(final JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! NewSchemaJob is finished, Time to verify the results");

            List<Employee> employees = Lists.newArrayList(employeeRepository.findAll());
//            final List<Employee> employees = cassandraTemplate.selectAll(Employee.class);
            log.info("First result is {}", employees.get(0));
        }
    }
}

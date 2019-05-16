package com.example.demo.batch;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;

import java.util.List;

public class CassandraBatchItemWriter<Employee> implements ItemWriter<Employee>, InitializingBean {

    protected static final Log logger = LogFactory.getLog(CassandraBatchItemWriter.class);
    private final Class<Employee> aClass;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Override
    public void afterPropertiesSet() throws Exception { }

    public CassandraBatchItemWriter(final Class<Employee> aClass) {
        this.aClass = aClass;
    }


    @Override
    public void write(final List<? extends Employee> items) throws Exception {
        logger.debug("Write operations is performing, the size is {}" + items.size());
        if (!items.isEmpty()) {
            logger.info("Deleting in a batch performing...");
//            cassandraTemplate.deleteAll(aClass);
            employeeRepository.deleteAll();
            logger.info("Inserting in a batch performing...");
            items.forEach(employee->employeeRepository.save((com.example.demo.model.Employee) employee));
        }

        logger.debug("Items is null...");
    }
}

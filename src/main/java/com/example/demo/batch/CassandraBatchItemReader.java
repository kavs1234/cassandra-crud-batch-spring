package com.example.demo.batch;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.google.common.collect.Lists;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;

import java.util.ArrayList;
import java.util.List;

public class CassandraBatchItemReader<Employee> implements ItemReader<Employee> {


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private CassandraOperations cassandraOperations;

    private final Class<Employee> aClass;

    private int index = 0;

    public CassandraBatchItemReader(final Class<Employee> aClass) {
        this.aClass = aClass;
    }

    @Override
    public Employee read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
//        List<Employee> employees = cassandraOperations.selectAll(aClass);
       List<Employee> employees = (List<Employee>) Lists.newArrayList(employeeRepository.findAll());


        if (index < employees.size()) {
            final Employee employee = employees.get(index);
            index++;
            return employee;
        }

        return null;
    }
}

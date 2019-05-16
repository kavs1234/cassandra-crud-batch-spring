package com.example.demo.batch;

import com.example.demo.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.util.Map;

import static com.google.common.collect.Maps.newHashMap;

public class EmployeeItemProcessor implements ItemProcessor<Employee, Employee> {

    private static final Logger log = LoggerFactory.getLogger(EmployeeItemProcessor.class);

    private Integer increment = 0;

    @Override
    public Employee process(final Employee employee) throws Exception {
        log.debug("Transformation is data performing, Object is {}", employee);

        final Map<String, String> brandMap = newHashMap();

        final Employee transformerEmployee = employee;
        transformerEmployee.setName(transformerEmployee.getName().toUpperCase());

        log.info("Converting {} into {}", employee.getName(), transformerEmployee.getName());
        return transformerEmployee;
    }
}

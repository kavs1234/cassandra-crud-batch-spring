package com.example.demo.controller;

import com.example.demo.common.Constants;
import com.example.demo.model.Employee;
import com.example.demo.model.Error;
import com.example.demo.repository.EmployeeRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    List<Employee> list() {
        return Lists.newArrayList(employeeRepository.findAll());
    }

    @RequestMapping(value = "/list/{name}", method = RequestMethod.GET)
    List<Employee> findByName(@PathVariable("name") String name ) {
        return Lists.newArrayList(employeeRepository.findByName(name));
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json")
    ResponseEntity<String> save(@RequestBody Employee e) {
        employeeRepository.save(e);
        return new ResponseEntity<String>("Success", HttpStatus.CREATED);

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    ResponseEntity<Employee> delete(@PathVariable("id") int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee e = new Employee();
        if (!optionalEmployee.isPresent()) {
            e.setErrorModel(new Error(Constants.DELETE_ERROR_1, Constants.INVALID_EMP_ID));
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
        employeeRepository.deleteById(id);
        e.setSuccessMessage("Success");

        return new ResponseEntity<>(e, HttpStatus.OK);
    }

    @RequestMapping(value = "updateSkills/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    ResponseEntity<Employee> updateSkills(@RequestBody List<String> skills, @PathVariable("id") int id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        Employee e;
        if (!optionalEmployee.isPresent()) {
            e = new Employee();
            e.setErrorModel(new Error(Constants.UPDATE_ERROR_1, "Invalid employee ID"));
            return new ResponseEntity<>(e, HttpStatus.NOT_FOUND);
        }
        e = optionalEmployee.get();
        e.setSkills(skills);
        Employee result = employeeRepository.save(e);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @RequestMapping(value = "/launchjob", method = RequestMethod.GET)
    public String handle() throws Exception {

        Logger logger = LoggerFactory.getLogger(this.getClass());
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

        return "Done!";
    }
}



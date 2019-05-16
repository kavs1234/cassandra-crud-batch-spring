package com.example.demo.repository;

import com.example.demo.model.Employee;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

    Optional<Employee> findByIdAndName(int id, String name);

    @Query("select * from Employee where name =:name ALLOW FILTERING")
    public Iterable<Employee> findByName(@Param("name") String categoryName);
}

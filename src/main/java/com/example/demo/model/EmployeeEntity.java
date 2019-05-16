package com.example.demo.model;

import com.datastax.driver.core.utils.UUIDs;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Table
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeEntity {

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @PrimaryKey
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    int id;

    UUID uuid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    String name;

    List<String> skills;

    public EmployeeEntity() {

    }

    public EmployeeEntity(int id, String name, List<String> skills) {
        this.id = id;
        this.name = name;
        this.skills = skills;
        this.uuid = UUIDs.timeBased();

    }

}

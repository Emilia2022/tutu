package com.web.model;

public class Employee {
    private long id;
    private String lastName;
    private String specialization;
    private long dep_id;

    public Employee() {
    }

    public Employee(long id, String lastName, String specialization, long dep_id) {
        this.id = id;
        this.lastName = lastName;
        this.specialization = specialization;
        this.dep_id = dep_id;
    }

    public long getId(long id) {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public long getDep_id() {
        return dep_id;
    }

    public void setDep_id(long dep_id) {
        this.dep_id = dep_id;
    }
}

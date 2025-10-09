package com.example.demo;

import java.sql.Date;

public class SelectCondition {

    private Integer id;
    private String name;
    private Integer age_min;
    private Integer age_max;
    private Date day_start_min;
    private Date day_start_max;
    private Date day_end_min;
    private Date day_end_max;

    public SelectCondition() {}

    // getter / setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getAge_min() { return age_min; }
    public void setAge_min(Integer age_min) { this.age_min = age_min; }

    public Integer getAge_max() { return age_max; }
    public void setAge_max(Integer age_max) { this.age_max = age_max; }

    public Date getDay_start_min() { return day_start_min; }
    public void setDay_start_min(Date day_start_min) { this.day_start_min = day_start_min; }

    public Date getDay_start_max() { return day_start_max; }
    public void setDay_start_max(Date day_start_max) { this.day_start_max = day_start_max; }

    public Date getDay_end_min() { return day_end_min; }
    public void setDay_end_min(Date day_end_min) { this.day_end_min = day_end_min; }

    public Date getDay_end_max() { return day_end_max; }
    public void setDay_end_max(Date day_end_max) { this.day_end_max = day_end_max; }
}
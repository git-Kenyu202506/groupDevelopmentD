package com.example.demo;

import java.sql.Date;

public class User {
    private Integer id;
    private String name;
    private String password;
    private Integer age;
    private Date day_start;
    private Date day_end;
    
    
    public User() {}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Date getDay_start() {
		return day_start;
	}

	public void setDay_start(Date day_start) {
		this.day_start = day_start;
	}

	public Date getDay_end() {
		return day_end;
	}

	public void setDay_end(Date day_end) {
		this.day_end = day_end;
	}

}
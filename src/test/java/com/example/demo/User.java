package com.example.demo;

import java.sql.Date;

public class User {
    private int id;
    private String name;
    private String password;
    private int age;
    private Date day_start;
    private Date day_end;
    
    
    public User() {}

	public User(int id, String name, String password, int age, Date day_start, Date day_end) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.age = age;
		this.day_start = day_start;
		this.day_end = day_end;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
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
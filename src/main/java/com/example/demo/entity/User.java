package com.example.demo.entity;

import java.time.LocalDate;

public class User {
		private Integer id;
	    private String password;
	    private String name;
	    private Integer age;
	    private LocalDate day_start;
	    private LocalDate day_end;

	    public Integer getId() {
	    	return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getAge() {
			return age;
		}
		public void setAge(Integer age) {
			this.age = age;
		}
		public LocalDate getDay_start() {
			return day_start;
		}
		public void setDay_start(LocalDate day_start) {
			this.day_start = day_start;
		}
		public LocalDate getDay_end() {
			return day_end;
		}
		public void setDay_end(LocalDate day_end) {
			this.day_end = day_end;
		}
}


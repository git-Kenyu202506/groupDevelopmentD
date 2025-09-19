package com.example.demo;

import java.time.LocalDate;

public class User {
		private int id;
	    private String password;
	    private String name;
	    private int age;
	    private LocalDate day_start;
	    private LocalDate day_end;

	    public int getId() {
	    	return id;
		}
		public void setId(int id) {
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
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
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


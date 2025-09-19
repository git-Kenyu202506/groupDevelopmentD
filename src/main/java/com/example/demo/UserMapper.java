package com.example.demo;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

	    @Insert("INSERT INTO users (id,password, name, age, day_start, day_end) " +
	            "VALUES (#{id},#{password}, #{name}, #{age}, #{day_start}, #{day_end})")
	    void insert(User user);

	    @Select("SELECT * FROM users WHERE name = #{name} AND password = #{password}")
	    Optional<User> findByNameAndPassword(@Param("name") String name, @Param("password") String password);
	}


package com.example.demo.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.demo.entity.User;

@Mapper
public interface UserMapper {

	    @Insert("INSERT INTO users (id,password, name, age, day_start, day_end) " +
	            "VALUES (#{id},#{password}, #{name}, #{age}, #{day_start}, #{day_end})")
	    void insert(User user);

	    @Select("SELECT * FROM users WHERE id = #{id} AND password = #{password}")
	    Optional<User> findByIdAndPassword(@Param("id") int id, @Param("password") String password);
	}


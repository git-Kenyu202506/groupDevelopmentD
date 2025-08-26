package com.example.demo;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
	
    @Select("SELECT * FROM member")
    List<User> selectAll();
    
}
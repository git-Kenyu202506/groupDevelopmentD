package com.example.demo;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface UserMapper {
	
    @SelectProvider(type = UserSqlProvider.class, method = "selectUserresult")
    List<User> selectUserresult(SelectCondition condition);
    
  
}
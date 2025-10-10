package com.example.demo;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface UserMapper {
	

    @Select("SELECT * FROM user WHERE id = #{id}") //id検索
    User selectById(int id);

    @SelectProvider(type = UserSqlProvider.class, method = "buildSelectByIds")//複数のid検索
    List<User> selectByIds(@Param("ids") List<Integer> ids);
    
    @SelectProvider(type = UserSqlProvider.class, method = "selectUserresult")//複数の条件検索
    List<User> selectUserresult(SelectCondition condition);
   
    

  
}
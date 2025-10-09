package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper mapper;


    public UserService(UserMapper mapper) {
        this.mapper = mapper;
    }
    
    public List<User> selectUserresult(SelectCondition condition) {
    	return mapper.selectUserresult(condition);
    }
}
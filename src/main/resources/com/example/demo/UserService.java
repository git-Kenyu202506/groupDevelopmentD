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
    
    public User selectById(int id) {
        return mapper.selectById(id);
    }
    
    public List<User> selectByIds(List<Integer> ids) {
        return mapper.selectByIds(ids);
    }

    
    public List<User> selectUserresult(SelectCondition condition) {
    	return mapper.selectUserresult(condition);
    }
}
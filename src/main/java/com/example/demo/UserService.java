package com.example.demo;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class UserService {
	 private final UserMapper userMapper;

	    public UserService(UserMapper userMapper) {
	        this.userMapper = userMapper;
	    }

	    public void register(User user) {
	        userMapper.insert(user);
	    }

	    public Optional<User> login(String name, String password) {
	        return userMapper.findByNameAndPassword(name, password);
	    }

}

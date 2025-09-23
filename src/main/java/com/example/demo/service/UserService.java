package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public Optional<User> login(int id, String password) {
        return userMapper.findByIdAndPassword(id, password);
    }

    public void register(User user) {
        userMapper.insert(user);
    }
}

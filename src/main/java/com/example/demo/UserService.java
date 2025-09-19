package com.example.demo;

import java.util.Optional;

import org.springframework.stereotype.Service;

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

package com.example.demo;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class UserController {
    @Autowired
    private UserService service;

    @RequestMapping("/selectAll")
    public String getAllUsers(Model m) {
        List<User> Users = service.selectAll();
        m.addAttribute("User", Users);
        return "selectAll";
    }
    
}


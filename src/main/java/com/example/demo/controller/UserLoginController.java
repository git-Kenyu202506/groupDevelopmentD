package com.example.demo.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
public class UserLoginController {
    private final UserService userService;

    public UserLoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, Model model) {
        if (user.getId() == null || user.getPassword() == null || user.getPassword().isBlank()) {
            model.addAttribute("error", "社員IDとパスワードを入力してください。");
            model.addAttribute("user", user);
            return "login";
        }

        Optional<User> loginUser = userService.login(user.getId(), user.getPassword());
        if (loginUser.isPresent()) {
            return "redirect:/menu";
        } else {
            model.addAttribute("error", "社員IDまたはパスワードが正しくありません。");
            model.addAttribute("user", user);
            return "login";
        }
    }

    @GetMapping("/menu")
    public String showMenu() {
        return "menu";
    }
}

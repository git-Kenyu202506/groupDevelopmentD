package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@Controller
@RequestMapping("/register")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/confirm")
    public String confirm(@ModelAttribute User user,
                          @RequestParam String confirmPassword,
                          Model model) {
        StringBuilder errors = new StringBuilder();

        if (user.getName() == null || user.getName().isEmpty()) {
            errors.append("社員名は必須入力です。");
        }
        if (user.getAge() == 0) {
            errors.append("年齢は必須入力です。");
        }
        if (user.getDay_start() == null) {
            errors.append("開始日は必須入力です。");
        }
        if (user.getDay_start() != null && user.getDay_end() != null &&
            user.getDay_start().isAfter(user.getDay_end())) {
            errors.append("開始日は終了日より後にできません。");
        }

        String password = user.getPassword();
        if (password == null || password.isEmpty()) {
            errors.append("パスワードは必須入力です。");
        } else if (password.length() < 8) {
            errors.append("パスワードは8文字以上で入力してください。");
        } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]+$")) {
            errors.append("パスワードは半角英数（大文字含む）で入力してください。");
        }

        if (!password.equals(confirmPassword)) {
            errors.append("パスワードと確認用が一致しません。");
        }

        if (errors.length() > 0) {
            model.addAttribute("error", errors.toString());
            model.addAttribute("user", user);
            return "register";
        }

        model.addAttribute("user", user);
        return "register-confirm";
    }

    @PostMapping("/back")
    public String back(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute User user) {
        userService.register(user);
        return "register-complete";
    }
}

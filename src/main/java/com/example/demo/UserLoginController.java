package com.example.demo;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        Optional<User> loginUser = userService.login(user.getId(), user.getPassword());
        if (loginUser.isPresent()) {
            return "redirect:/menu";
        } else {
            model.addAttribute("error", "社員IDまたはパスワードが正しくありません。");
            model.addAttribute("user", user); // 入力保持
            return "login";
        }
    }

    @GetMapping("/menu")
    public String showMenu() {
        return "menu";
    }
}

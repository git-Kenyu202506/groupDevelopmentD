package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String name,
                        @RequestParam String password,
                        Model model) {
        return userService.login(name, password)
                .map(user -> {
                    model.addAttribute("user", user);
                    return "menu"; // 成功時
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "名前またはパスワードが正しくありません");
                    return "login";
                });
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user,
                           @RequestParam String confirmPassword,
                           Model model) {
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "パスワードと確認用パスワードが一致しません");
            return "register";
        }
        userService.register(user);
        return "redirect:/login";
    }

}

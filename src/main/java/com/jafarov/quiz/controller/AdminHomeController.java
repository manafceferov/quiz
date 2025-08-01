package com.jafarov.quiz.controller;

import com.jafarov.quiz.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController extends BaseController {

    private final UserService userService;

    public AdminHomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String index(Model model) {
        return "admin/home";
    }
}

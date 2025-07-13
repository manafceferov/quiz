package com.jafarov.quiz.admin.controller;

import com.jafarov.quiz.admin.entity.User;
import com.jafarov.quiz.admin.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class HomeController extends BaseController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String index(Model model) {
        return "admin/home";
    }

}

package com.quiz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminAuthController extends BaseController {

    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal != null) {
            return "redirect:/admin/home";
        }
        return "admin/login";
    }
}

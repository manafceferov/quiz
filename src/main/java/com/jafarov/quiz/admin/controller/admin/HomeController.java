package com.jafarov.quiz.admin.controller.admin;

import ch.qos.logback.core.model.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HomeController {

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        return "admin/dashboard"; // Admin səhifəsi
    }

}

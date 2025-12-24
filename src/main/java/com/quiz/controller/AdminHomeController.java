package com.quiz.controller;

import com.quiz.service.AdminService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminHomeController extends BaseController {

    private final AdminService adminService;

    public AdminHomeController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/home")
    public String index(Model model) {
        return "admin/home";
    }
}

package com.jafarov.quiz.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @GetMapping()
    public String index() {
        return "admin/user/index";
    }

    @GetMapping("/create")
    public String create() {
        return "admin/user/create";
    }

    @PostMapping()
    public String create(Principal principal) {
        return "admin/user/index";
    }

    @GetMapping("/edit")
    public String edit() {
        return "admin/user/edit";
    }

    @PutMapping("/edit")
    public String edit(Principal principal) {
        return "admin/user/edit";
    }

    @DeleteMapping()
    public String delete() {
        return "admin/user/index";
    }
}

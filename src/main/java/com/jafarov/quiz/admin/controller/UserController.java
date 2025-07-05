package com.jafarov.quiz.admin.controller;

import com.jafarov.quiz.admin.dto.user.UserIUDRequest;
import com.jafarov.quiz.admin.entity.User;
import com.jafarov.quiz.admin.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "1") int size) {
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("users", userService.getAll(pageable));
        return "admin/user/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        return "admin/user/create";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("userIUDRequest") UserIUDRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/user/create";
        }

        userService.createUser(request);
        return "redirect:/admin/users";
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

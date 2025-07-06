package com.jafarov.quiz.admin.controller;

import com.jafarov.quiz.admin.dto.user.UserIUDRequest;
import com.jafarov.quiz.admin.entity.User;
import com.jafarov.quiz.admin.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("users", userService.getAll(pageable));
        return "admin/user/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("userIUDRequest", new UserIUDRequest());
        return "admin/user/create";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("userIUDRequest") UserIUDRequest request,
                         BindingResult bindingResult,
                         @RequestParam("file") MultipartFile file,
                         Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/user/create";
        }

        userService.save(request, file);
        return "redirect:/admin/users";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "admin/user/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("userIUDRequest") UserIUDRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("user", request);
            return "admin/user/edit";
        }

        userService.update(request);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin/users";
    }
}

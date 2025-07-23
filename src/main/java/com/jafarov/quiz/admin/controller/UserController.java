package com.jafarov.quiz.admin.controller;

import com.jafarov.quiz.admin.dto.user.UserInsertRequest;
import com.jafarov.quiz.admin.dto.user.UserUpdateRequest;
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
public class UserController extends BaseController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(required = false) String name,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("param", name);
        model.addAttribute("users", userService.searchUsers(name, pageable));
        return "admin/user/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("userIUDRequest", new UserInsertRequest());
        return "admin/user/create";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("userIUDRequest")
                         UserInsertRequest request,
                         BindingResult bindingResult,
                         @RequestParam("file") MultipartFile file,
                         Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/user/create";
        }

        request.setFile(file);
        userService.save(request);
        return "redirect:/admin/users";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        User user = userService.edit(id);
        model.addAttribute("userIUDRequest", user);
        return "admin/user/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("userIUDRequest")
                       UserUpdateRequest request,
                       BindingResult bindingResult,
                       Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("user", userService.edit(request.getId()));
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

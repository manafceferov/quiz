package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.admin.AdminInsertRequest;
import com.jafarov.quiz.dto.admin.AdminUpdateRequest;
import com.jafarov.quiz.entity.Admin;
import com.jafarov.quiz.service.AdminService;
import com.jafarov.quiz.util.session.ParticipantSessionData;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

@Controller
@Validated
@RequestMapping("/admin/users")
public class AdminController extends BaseController {

    private final AdminService adminService;
    private final ParticipantSessionData participantSessionData;

    public AdminController(AdminService adminService,
                           ParticipantSessionData participantSessionData
    ) {
        this.adminService = adminService;
        this.participantSessionData = participantSessionData;
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(required = false) String name,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("param", name);
        model.addAttribute("users", adminService.searchUsers(name, pageable));
        return "admin/user/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("userIUDRequest", new AdminInsertRequest());
        return "admin/user/create";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("userIUDRequest")
                         AdminInsertRequest request,
                         BindingResult bindingResult,
                         @RequestParam("file") MultipartFile file,
                         Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/user/create";
        }
        request.setFile(file);
        adminService.save(request);
        return "redirect:/admin/users";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long id,
                       Model model
    ) {
        Admin admin = adminService.edit(id);
        model.addAttribute("userIUDRequest", admin);
        return "admin/user/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("userIUDRequest")
                       AdminUpdateRequest request,
                       BindingResult bindingResult,
                       Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("user", adminService.edit(request.getId()));
            return "admin/user/edit";
        }
        adminService.update(request);
        return "redirect:/admin/users";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        adminService.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/change-status/user/{id}/status/{status}")
    public String changeStatus(@PathVariable Long id,
                               @PathVariable Boolean status
    ) {
        adminService.changeStatus(id, status);
        return "redirect:/admin/users";
    }
}

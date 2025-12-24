package com.jafarov.quiz.controller;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import com.jafarov.quiz.service.ParticipantService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ParticipantAuthController extends BaseController{

    private final ParticipantService participantService;

    public ParticipantAuthController(ParticipantService participantService
    ) {
        this.participantService = participantService;
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated())
            return "redirect:/";

        return "participant/login";
    }

    @PostMapping("/register")
    public String register(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("confirm-password") String confirmPassword,
                           Model model,
                           RedirectAttributes redirectAttributes
    ) {
        try {
            participantService.register(firstName, lastName, email, password, confirmPassword);
            redirectAttributes.addFlashAttribute("success", "Qeydiyyatdan keçdiniz");
            return "redirect:/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "/login";
        }
    }
}

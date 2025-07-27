package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.topic.TopicWithQuestionCountProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.service.ParticipantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class ParticipantAuthController {

    private final ParticipantService participantService;

    public ParticipantAuthController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping("/login")
    public String login(Principal principal) {
        if (principal != null)
            return "redirect:/participant/home";

        return "participant/login";
    }

    @PostMapping("/login")
    public String auth() {
        return "redirect:/home";
    }

    @PostMapping("/register")
    public String register(@RequestParam("firstName") String firstName,
                           @RequestParam("lastName") String lastName,
                           @RequestParam("email") String email,
                           @RequestParam("password") String password,
                           @RequestParam("confirm-password") String confirmPassword,
                           @RequestParam(value = "file", required = false) MultipartFile file,
                           Model model,
                           RedirectAttributes redirectAttributes
    ) {
        try {
            participantService.register(firstName, lastName, email, password, confirmPassword, file);
            redirectAttributes.addFlashAttribute("success", "Qeydiyyatdan keçdiniz");
            return "redirect:/participant/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "participant/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/participant/login";
    }
}
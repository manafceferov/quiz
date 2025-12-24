package com.quiz.controller;

import com.quiz.dto.profile.ProfileProjectionEditDto;
import com.quiz.service.ParticipantService;
import com.quiz.util.session.ParticipantSessionData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/participants/profile")
public class ParticipantProfileController extends BaseController{

    private final ParticipantService participantService;
    private final ParticipantSessionData participantSessionData;

    public ParticipantProfileController(ParticipantService participantService,
                                        ParticipantSessionData participantSessionData

    ) {
        this.participantService = participantService;
        this.participantSessionData = participantSessionData;
    }

    @GetMapping
    public String getProfile(Model model) {
        System.out.println("Thread : " + Thread.currentThread());
        ProfileProjectionEditDto dto = participantService.getProfile(participantSessionData.getId());
        model.addAttribute("participantEditDto", dto);
        return "participant/profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute("participantEditDto") ProfileProjectionEditDto dto,
            RedirectAttributes redirectAttributes
    ) {
        try {
            participantService.updateProfile(dto);
            redirectAttributes.addFlashAttribute("message", "Profil məlumatları uğurla yeniləndi");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/participants/profile";
    }
}

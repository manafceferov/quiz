package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.profile.ProfileProjectionEditDto;
import com.jafarov.quiz.mapper.ParticipantMapper;
import com.jafarov.quiz.service.ParticipantService;
import com.jafarov.quiz.util.session.ParticipantSessionData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/participants/profile")
public class ParticipantProfileController extends BaseController{

    private final ParticipantService participantService;
    private final ParticipantSessionData participantSessionData;
    private final ParticipantMapper participantMapper;

    public ParticipantProfileController(ParticipantService participantService,
                                        ParticipantSessionData participantSessionData,
                                        ParticipantMapper participantMapper
    ) {
        this.participantService = participantService;
        this.participantSessionData = participantSessionData;
        this.participantMapper = participantMapper;
    }

    @GetMapping
    public String getProfile(Model model) {
        ProfileProjectionEditDto dto = participantService.getProfile(participantSessionData.getId());
        model.addAttribute("participantEditDto", dto);
        return "participant/profile";
    }

    @PostMapping("/update")
    public String updateProfile(
            @ModelAttribute("participantEditDto") ProfileProjectionEditDto dto,
            Model model
    ) {
        System.out.println("Update profile method called");
        try {
            participantService.updateProfile(dto);
            model.addAttribute("message", "Profil məlumatları uğurla yeniləndi");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("participantEditDto", participantService.getProfile(participantSessionData.getId()));
        return "participant/profile";
    }
}

package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.participant.ParticipantListDto;
import com.jafarov.quiz.service.ParticipantService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/admin/participants")
public class ParticipantDetailsController {

    private final ParticipantService participantService;

    public ParticipantDetailsController(ParticipantService participantService
    ) {
        this.participantService = participantService;
    }

    @GetMapping
    public String index(Model model,
                        Pageable pageable
    ) {
        Page<ParticipantListDto> participants = participantService.getAll(pageable);
        model.addAttribute("participants", participants);
        return "admin/participant/index";
    }

    @PostMapping("/{id}/status")
    public String changeStatus(@PathVariable Long id,
                               @RequestParam Boolean status
    ) {
        participantService.changeStatus(id, status);
        return "redirect:/admin/participants";
    }
}

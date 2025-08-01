package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.topic.TopicWithQuestionCountProjection;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.service.ParticipantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class ParticipantHomeController extends BaseController{

    private final ParticipantService participantService;

    public ParticipantHomeController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping("/")
    public String loginPage(@RequestParam(defaultValue = "0") int page,
                            @RequestParam(defaultValue = "6") int size,
                            Model model,
                            Authentication authentication) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TopicWithQuestionCountProjection> topicsPage = participantService.getAllTopics(pageable);

        model.addAttribute("topics", topicsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", topicsPage.getTotalPages());

        // Burada participant-i əlavə edirik
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Participant participant = participantService.findByEmail(userDetails.getUsername());
            model.addAttribute("participant", participant);
        }

        return "participant/home";
    }

}



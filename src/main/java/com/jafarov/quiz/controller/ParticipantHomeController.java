package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.topic.TopicWithQuestionCountProjection;
import com.jafarov.quiz.dto.topic.TopicWithQuestionCountProjectionDto;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.service.ParticipantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/participant")
public class ParticipantHomeController {

    private final ParticipantService participantService;

    public ParticipantHomeController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping("/home")
    public String homePage(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "6") int size,
                           HttpSession session,
                           Model model) {

        Participant participant = (Participant) session.getAttribute("participant");
        if (participant == null) {
            return "redirect:/participant/login";
        }

        Pageable pageable = PageRequest.of(page, size);
        // ParticipantService-dəki mövcud metodu istifadə edirik
        Page<TopicWithQuestionCountProjection> topicsPage = participantService.getAllTopics(pageable);

        model.addAttribute("participant", participant);
        model.addAttribute("topics", topicsPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", topicsPage.getTotalPages());

        return "participant/home";
    }

}



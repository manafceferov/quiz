package com.quiz.controller;

import com.quiz.dto.topic.TopicWithQuestionCountProjection;
import com.quiz.entity.Participant;
import com.quiz.service.ParticipantService;
import com.quiz.service.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Controller
public class ParticipantHomeController extends BaseController {

    private final ParticipantService participantService;
    private final TopicService topicService;

    public ParticipantHomeController(ParticipantService participantService,
                                     TopicService topicService
    ) {
        this.participantService = participantService;
        this.topicService = topicService;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            Model model,
            Authentication authentication
    ) {
        Pageable pageable = PageRequest.of(page, 10);

        Page<TopicWithQuestionCountProjection> topicPage =
                topicService.getTopics(name, pageable);
        model.addAttribute("topics", topicPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", topicPage.getTotalPages());
        model.addAttribute("name", name);

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Participant participant =
                    participantService.findByEmail(userDetails.getUsername());
            model.addAttribute("participant", participant);
        }

        return "participant/home";
    }
}



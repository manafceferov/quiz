package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.topic.TopicWithQuestionCountProjection;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.service.ParticipantService;
import com.jafarov.quiz.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Controller
public class ParticipantHomeController extends BaseController{

    private final ParticipantService participantService;
    private final TopicService topicService;

    public ParticipantHomeController(ParticipantService participantService,
                                     TopicService topicService) {
        this.participantService = participantService;
        this.topicService = topicService;
    }

    @GetMapping("/")
    public String loginPage(@RequestParam(required = false) String name,
                            Model model,
                            Authentication authentication) {

        List<TopicWithQuestionCountProjection> topics;

        if (name != null && !name.isBlank()) {
            topics = topicService.searchTopicsWithQuestionCount(name);
        } else {
            topics = topicService.getAllWithQuestionCount();
        }

        model.addAttribute("topics", topics);

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Participant participant = participantService.findByEmail(userDetails.getUsername());
            model.addAttribute("participant", participant);
        }

        return "participant/home";
    }

}



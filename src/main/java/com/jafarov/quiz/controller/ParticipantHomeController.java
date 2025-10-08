package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.participant.ParticipantEditDto;
import com.jafarov.quiz.dto.topic.TopicWithQuestionCountProjection;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.mapper.ParticipantMapper;
import com.jafarov.quiz.service.ParticipantService;
import com.jafarov.quiz.service.TopicService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class ParticipantHomeController extends BaseController {

    private final ParticipantService participantService;
    private final TopicService topicService;
    private final ParticipantMapper participantMapper;

    public ParticipantHomeController(
            ParticipantService participantService,
            TopicService topicService,
            ParticipantMapper participantMapper
    ) {
        this.participantService = participantService;
        this.topicService = topicService;
        this.participantMapper = participantMapper;
    }

    @GetMapping("/")
    public String loginPage(@RequestParam(required = false)
                            String name,
                            Model model,
                            Authentication authentication,
                            Pageable pageable) {

        List<TopicWithQuestionCountProjection> topics;
        topics = topicService.searchTopicsWithQuestionCount(name);
        topicService.getAllTopics(pageable);
        model.addAttribute("topics", topics);

        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Participant participant = participantService.findByEmail(userDetails.getUsername());
            model.addAttribute("participant", participant);
        }


        return "participant/home";
    }

//    @GetMapping("/profile")
//    public String showProfile(Authentication authentication,
//                              Model model
//    ) {
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return "redirect:/login";
//        }
//
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        Participant participant = participantService.findByEmail(userDetails.getUsername());
//        ParticipantEditDto participantEditDto = participantMapper.toDto(participant);
//        model.addAttribute("participantEditDto", participantEditDto);
//        return "participant/profile";
//    }
//
//    @PostMapping("/profile")
//    public String updateProfile(
//            @Valid @ModelAttribute ParticipantEditDto participantEditDto,
//            BindingResult bindingResult,
//            @RequestParam("attachment") MultipartFile attachment,
//            Authentication authentication,
//            RedirectAttributes redirectAttributes
//    ) {
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return "redirect:/login";
//        }
//
//        if (bindingResult.hasErrors()) {
//            return "participant/profile";
//        }
//
//        try {
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            Participant participant = participantService.findByEmail(userDetails.getUsername());
//            participantService.updateProfile(participant.getId(), participantEditDto, attachment);
//            redirectAttributes.addFlashAttribute("message", "Profil məlumatları uğurla yeniləndi");
//        } catch (IllegalArgumentException e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//
//        return "redirect:/participant/profile";
//    }
}



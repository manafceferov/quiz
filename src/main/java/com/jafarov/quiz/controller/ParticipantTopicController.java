package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.topic.TopicInsertRequest;
import com.jafarov.quiz.dto.topic.TopicUpdateRequest;
import com.jafarov.quiz.entity.Topic;
import com.jafarov.quiz.service.TopicService;
import com.jafarov.quiz.util.session.AuthSessionData;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/participant/topics")
public class ParticipantTopicController {

    private final TopicService service;
    private final AuthSessionData authSessionData;

    public ParticipantTopicController(TopicService service,
                                      AuthSessionData authSessionData
    ) {
        this.service = service;
        this.authSessionData = authSessionData;
    }

    @GetMapping
    public String index(@RequestParam(required = false) String name,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("topics", service.getAll(name, pageable));
        model.addAttribute("name", name);
        model.addAttribute("currentParticipantId", authSessionData.getParticipantSessionData().getId());
        return "participant/topic/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("request", new TopicInsertRequest());
        return "participant/topic/create";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("request") TopicInsertRequest request,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes redirect
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "participant/topic/create";
        }
        request.setByParticipant(authSessionData.getParticipantSessionData().getId());
        service.save(request);
        redirect.addFlashAttribute("success", "Mövzu əlavə edildi");
        return "redirect:/participant/topics";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       Model model
    ) {
        Topic t = service.findById(id).orElseThrow();
        if (!t.getByParticipant().equals(authSessionData.getParticipantSessionData().getId())) {
            return "redirect:/participant/topics";
        }
        model.addAttribute("request", t);
        return "participant/topic/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("request") TopicUpdateRequest request,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirect
    ) {
        Topic topic = service.findById(request.getId()).orElseThrow();
        if (!topic.getByParticipant().equals(authSessionData.getParticipantSessionData().getId())) {
            return "redirect:/participant/topics";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "participant/topic/edit";
        }
        request.setByParticipant(authSessionData.getParticipantSessionData().getId());
        service.update(request);
        redirect.addFlashAttribute("success", "Mövzu yeniləndi");
        return "redirect:/participant/topics";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         RedirectAttributes redirect
    ) {
        Topic t = service.findById(id).orElseThrow();
        if (!t.getByParticipant().equals(authSessionData.getParticipantSessionData().getId())) {
            return "redirect:/participant/topics";
        }
        service.deleteById(id);
        redirect.addFlashAttribute("success", "Mövzu silindi");
        return "redirect:/participant/topics";
    }

    @GetMapping("/change-status/topic/{id}/status/{status}")
    public String changeStatus(@PathVariable Long id,
                               @PathVariable Boolean status,
                               RedirectAttributes redirect
    ) {
        Topic t = service.findById(id).orElseThrow();
        if (!t.getByParticipant().equals(authSessionData.getParticipantSessionData().getId())) {
            return "redirect:/participant/topics";
        }
        service.changeStatus(id, status);
        return "redirect:/participant/topics";
    }
}
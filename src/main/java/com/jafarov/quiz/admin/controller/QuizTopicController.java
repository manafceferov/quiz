package com.jafarov.quiz.admin.controller;

import com.jafarov.quiz.admin.dto.topic.QuizTopicInsertRequest;
import com.jafarov.quiz.admin.dto.topic.QuizTopicUpdateRequest;
import com.jafarov.quiz.admin.entity.Topic;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import com.jafarov.quiz.admin.service.QuizTopicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/topics")
public class QuizTopicController {

    private final QuizTopicService service;

    public QuizTopicController(QuizTopicService service) {
        this.service = service;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("topic", new QuizTopicInsertRequest());
        return "topic/add";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("topic",
                service.getById(id));
        return "admin/topic/edit";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("request", new QuizTopicInsertRequest());
        return "admin/topic/create";
    }

    @PostMapping()
    public String create(@javax.validation.Valid @ModelAttribute("request") QuizTopicInsertRequest request,
                         BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/topic/create";
        }

        service.save(request);
        return "redirect:/admin/topics";
    }

    @GetMapping
    public String index(@RequestParam(required = false) String name,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {

        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("topics", service.getAll(name, pageable));
        model.addAttribute("param", name);
        return "admin/topic/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Topic topic = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found: " + id));

        model.addAttribute("request", topic);
        return "admin/topic/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("request") QuizTopicUpdateRequest request,
                       BindingResult bindingResult,
                       Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("topic", service.getById(request.getId()));
            return "admin/topic/edit";
        }

        service.update(request);
        return "redirect:/admin/topics";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/admin/topics";
    }
}

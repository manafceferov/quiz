package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.topic.TopicInsertRequest;
import com.jafarov.quiz.dto.topic.TopicUpdateRequest;
import com.jafarov.quiz.entity.Topic;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import com.jafarov.quiz.service.TopicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/topics")
public class TopicController extends BaseController{

    private final TopicService service;

    public TopicController(TopicService service) {
        this.service = service;
    }

    @GetMapping
    public String index(@RequestParam(required = false) String name,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("topics", service.getAll(name, pageable));
        model.addAttribute("param", name);
        return "admin/topic/index";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("topic", new TopicInsertRequest());
        return "topic/add";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,
                               Model model
    ) {
        model.addAttribute("topic",
                service.getById(id));
        return "admin/topic/edit";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("request", new TopicInsertRequest());
        return "admin/topic/create";
    }

    @PostMapping()
    public String create(@Valid
                         @ModelAttribute("request")
                         TopicInsertRequest request,
                         BindingResult bindingResult,
                         Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/topic/create";
        }

        service.save(request);
        return "redirect:/admin/topics";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id")
                       Long id,
                       Model model
    ) {
        Topic topic = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Topic not found: " + id));

        model.addAttribute("request", topic);
        return "admin/topic/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("request")
                       TopicUpdateRequest request,
                       BindingResult bindingResult,
                       Model model
    ) {
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

    @GetMapping("/change-status/topic/{id}/status/{status}")
    public String changeStatus(@PathVariable Long id,
                               @PathVariable Boolean status
    ) {
        service.changeStatus(id, status);
        return "redirect:/admin/topics";
    }
}


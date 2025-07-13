package com.jafarov.quiz.admin.controller;

import com.jafarov.quiz.admin.dto.question.QuestionEditDto;
import com.jafarov.quiz.admin.dto.question.QuestionInsertRequest;
import com.jafarov.quiz.admin.dto.question.QuestionUpdateRequest;
import com.jafarov.quiz.admin.entity.Question;
import com.jafarov.quiz.admin.service.QuestionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/questions")
public class QuestionController {

    private final QuestionService service;

    public QuestionController(QuestionService service) {
        this.service = service;
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("question", new QuestionInsertRequest());
        return "question/add";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        QuestionEditDto question = service.getById(id);

        model.addAttribute("question", question);
        return "admin/question/edit";
    }

    @GetMapping("/topic/{topicId}/create")
    public String create(@PathVariable() Long topicId, Model model) {

        QuestionInsertRequest request = new QuestionInsertRequest();
        request.setTopicId(topicId);
        model.addAttribute("request", request);

        return "admin/question/create";
    }

    @PostMapping()
    public String create(
            @Valid @ModelAttribute("request") QuestionInsertRequest request,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/question/create";
        }

        service.save(request);

        redirectAttributes.addFlashAttribute("success", "Uğurla əlavə edildi");
        redirectAttributes.addAttribute("topicId", request.getTopicId());
        return "redirect:/admin/questions/topic/{topicId}";
    }

    @GetMapping("/topic/{topicId}")
    public String index(@PathVariable() Long topicId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        Model model) {
        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("questions", service.getQuestionsByTopicId(topicId, pageable));
        model.addAttribute("topicId", topicId);
        return "admin/question/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        Question question = service.findById(id)
                .orElseThrow(() -> new RuntimeException("Sual tapılmadı: " + id));

        model.addAttribute("request", question);
        return "admin/question/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("request") QuestionUpdateRequest request,
                       BindingResult bindingResult,
                       Model model,
                       RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("question", service.getById(request.getId()));
            return "admin/question/edit";
        }

        service.update(request);

        redirectAttributes.addFlashAttribute("success", "Uğurla yeniləndi");
        redirectAttributes.addAttribute("topicId", request.getTopicId());
        return "redirect:/admin/questions/topic/{topicId}";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        service.deleteById(id);
        return "redirect:/admin/questions";
    }
}

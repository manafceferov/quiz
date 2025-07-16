package com.jafarov.quiz.admin.controller;

import com.jafarov.quiz.admin.dto.answer.AnswerInsertRequest;
import com.jafarov.quiz.admin.dto.question.*;
import com.jafarov.quiz.admin.entity.Answer;
import com.jafarov.quiz.admin.service.AnswerService;
import com.jafarov.quiz.admin.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/questions")
public class QuestionController {

    private final QuestionService service;
    private final AnswerService answerService;

    public QuestionController(QuestionService service, AnswerService answerService) {
        this.service = service;
        this.answerService = answerService;
    }

    @GetMapping("/topic/{topicId}")
    public String index(@PathVariable Long topicId,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size,
                        @RequestParam(required = false) String keyword,
                        Model model) {

        Pageable pageable = PageRequest.of(page, size);
        model.addAttribute("questions", service.searchQuestionsByTopicAndKeyword(topicId, keyword, pageable));
        model.addAttribute("topicId", topicId);
        model.addAttribute("keyword", keyword);
        return "admin/question/index";
    }

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        model.addAttribute("question", service.getById(id));
        model.addAttribute("answers", answerService.getAnswersByQuestionId(id));
        return "admin/question/view";
    }

    @GetMapping("/topic/{topicId}/create")
    public String create(@PathVariable Long topicId, Model model) {
        QuestionInsertRequest request = new QuestionInsertRequest();
        request.setTopicId(topicId);
        model.addAttribute("request", request);
        return "admin/question/create";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("request") QuestionInsertRequest request,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/question/create";
        }
        service.save(request);
        redirectAttributes.addFlashAttribute("success", "Sual əlavə edildi");
        redirectAttributes.addAttribute("topicId", request.getTopicId());
        return "redirect:/admin/questions/topic/{topicId}";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        QuestionEditDto dto = service.getById(id);
        model.addAttribute("request", dto);

        model.addAttribute("answers", answerService.getAnswersByQuestionId(id));
        AnswerInsertRequest newAnswer = new AnswerInsertRequest();
        newAnswer.setQuestionId(id);
        model.addAttribute("newAnswer", newAnswer);

        return "admin/question/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("request") QuestionUpdateRequest request,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes, Model m) {
        if (bindingResult.hasErrors()) {
            m.addAttribute("errors", bindingResult.getAllErrors());
            return "admin/question/edit";
        }
        service.update(request);
        redirectAttributes.addFlashAttribute("success", "Sual yeniləndi");
        redirectAttributes.addAttribute("topicId", request.getTopicId());
        return "redirect:/admin/questions/topic/{topicId}";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, @RequestParam Long topicId,
                         RedirectAttributes redirectAttributes) {
        service.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Sual silindi");
        redirectAttributes.addAttribute("topicId", topicId);
        return "redirect:/admin/questions/topic/{topicId}";
    }

    @GetMapping("/answers/{id}")
    @ResponseBody
    public List<Answer> answersJson(@PathVariable("id") Long id) {
        return answerService.getAnswersByQuestionId(id);
    }

    @PostMapping("/answers/create")
    public String answer(@ModelAttribute("newAnswer") AnswerInsertRequest request,
                            RedirectAttributes redirectAttributes) {
        answerService.insert(request);
        redirectAttributes.addFlashAttribute("success", "Cavab əlavə edildi");
        redirectAttributes.addAttribute("id", request.getQuestionId());
        return "redirect:/admin/questions/{id}/edit";
    }

    @PostMapping("/answers/{id}/delete")
    public String answer(@PathVariable Long id,
                               @RequestParam Long questionId,
                               RedirectAttributes redirectAttributes) {
        answerService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Cavab silindi");
        redirectAttributes.addAttribute("id", questionId);
        return "redirect:/admin/questions/{id}/edit";
    }


}
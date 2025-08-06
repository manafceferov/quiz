package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.exam.AnswerSubmitRequest;
import com.jafarov.quiz.dto.exam.QuestionExamDto;
import com.jafarov.quiz.entity.Answer;
import com.jafarov.quiz.service.ExamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exam")
@SessionAttributes("answers")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @ModelAttribute("answers")
    public List<AnswerSubmitRequest> initAnswers() {
        return new ArrayList<>();
    }

    @GetMapping("/{topicId}/question/{questionIndex}")
    public String showQuestion(
            @PathVariable Long topicId,
            @PathVariable int questionIndex,
            Model model,
            @ModelAttribute("answers") List<AnswerSubmitRequest> answers) {

        List<QuestionExamDto> questions = examService.getAllExamQuestions(topicId);

        if (questionIndex < 0) {
            return "redirect:/exam/" + topicId + "/question/0";
        }

        if (questionIndex >= questions.size()) {
            int score = examService.checkExamAnswers(answers);
            model.addAttribute("isExamFinished", true);
            model.addAttribute("score", score);
            model.addAttribute("totalQuestions", questions.size());
            return "participant/exam";
        }

        QuestionExamDto currentQuestion = questions.get(questionIndex);
        model.addAttribute("question", currentQuestion);
        model.addAttribute("questionIndex", questionIndex);
        model.addAttribute("topicId", topicId);
        model.addAttribute("isExamFinished", false);

        return "participant/exam";
    }


    @PostMapping("/{topicId}/question/{questionIndex}/submit")
    @ResponseBody
    public Map<String, Object> submitAnswer(@PathVariable Long topicId,
                                            @PathVariable int questionIndex,
                                            @RequestParam Long answerId,
                                            @ModelAttribute("answers") List<AnswerSubmitRequest> answers) {

        List<QuestionExamDto> questions = examService.getAllExamQuestions(topicId);

        if (questionIndex >= questions.size()) {
            return Map.of("error", "No more questions");
        }

        Long questionId = questions.get(questionIndex).getId();
        answers.add(new AnswerSubmitRequest(questionId, answerId));


        List<Answer> answerList = examService.getActiveAnswersByQuestionId(questionId);
        Long correctAnswerId = answerList.stream()
                .filter(Answer::isCorrect)
                .findFirst()
                .map(Answer::getId)
                .orElse(null);

        return Map.of(
                "correctAnswerId", correctAnswerId
        );
    }


    @GetMapping("/result/{topicId}")
    public String showResult(@PathVariable Long topicId,
                             @ModelAttribute("answers") List<AnswerSubmitRequest> userAnswers,
                             Model model,
                             SessionStatus sessionStatus) {
        List<QuestionExamDto> questions = examService.getAllExamQuestions(topicId);
        int correctCount = examService.checkExamAnswers(userAnswers);

        model.addAttribute("questions", questions);
        model.addAttribute("userAnswers", userAnswers);
        model.addAttribute("score", correctCount);
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("isExamFinished", true);

        sessionStatus.setComplete();
        return "participant/exam_result";
    }


}

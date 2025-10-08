//package com.jafarov.quiz.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.jafarov.quiz.dto.exam.AnswerSubmitRequest;
//import com.jafarov.quiz.dto.exam.QuestionExamDto;
//import com.jafarov.quiz.entity.Answer;
//import com.jafarov.quiz.service.ExamService;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Controller
//@RequestMapping("/exam")
//public class ExamController {
//
//    private final ExamService examService;
//    private final ObjectMapper objectMapper;
//
//    public ExamController(ExamService examService, ObjectMapper objectMapper) {
//        this.examService = examService;
//        this.objectMapper = objectMapper;
//    }
//
//    // Yeni endpoint: /exam/{topicId}/question/{questionIndex}
//    @GetMapping("/{topicId}/question/{questionIndex}")
//    public String showExamWithIndex(@PathVariable Long topicId,
//                                    @PathVariable int questionIndex,
//                                    Model model) {
//        List<QuestionExamDto> questions = examService.getAllExamQuestions(topicId);
//        if (questionIndex < 0 || questionIndex >= questions.size()) {
//            return "redirect:/exam/" + topicId + "/question/0";
//        }
//
//        QuestionExamDto question = questions.get(questionIndex);
//
//        model.addAttribute("question", question);
//        model.addAttribute("questions", questions);
//        model.addAttribute("topicId", topicId);
//        model.addAttribute("questionIndex", questionIndex);
//        model.addAttribute("totalQuestions", questions.size());
//        model.addAttribute("isExamFinished", false);
//
//        return "participant/exam";
//    }
//
//    // Əsas endpoint: /exam/{topicId}/question
//    @GetMapping("/{topicId}/question")
//    public String showExam(@PathVariable Long topicId, Model model) {
//        List<QuestionExamDto> questions = examService.getAllExamQuestions(topicId);
//        if (questions.isEmpty()) {
//            return "redirect:/"; // əgər sual yoxdursa
//        }
//
//        QuestionExamDto question = questions.get(0);
//
//        model.addAttribute("question", question);
//        model.addAttribute("questions", questions);
//        model.addAttribute("topicId", topicId);
//        model.addAttribute("questionIndex", 0);
//        model.addAttribute("totalQuestions", questions.size());
//        model.addAttribute("isExamFinished", false);
//
//        return "participant/exam";
//    }
//
//    @PostMapping("/{topicId}/question/{questionIndex}/submit")
//    @ResponseBody
//    public Map<String, Object> submitAnswer(@PathVariable Long topicId,
//                                            @PathVariable int questionIndex,
//                                            @RequestParam Long answerId) {
//        List<QuestionExamDto> questions = examService.getAllExamQuestions(topicId);
//        if (questionIndex >= questions.size()) {
//            return Map.of("error", "No more questions");
//        }
//
//        Long questionId = questions.get(questionIndex).getId();
//        List<Answer> answerList = examService.getActiveAnswersByQuestionId(questionId);
//
//        // təhlükəsizlik üçün yoxlama
//        boolean valid = answerList.stream().anyMatch(a -> a.getId().equals(answerId));
//        if (!valid) {
//            return Map.of("error", "Invalid answer");
//        }
//
//        Long correctAnswerId = answerList.stream()
//                .filter(Answer::isCorrect)
//                .findFirst()
//                .map(Answer::getId)
//                .orElse(null);
//
//        return Map.of("correctAnswerId", correctAnswerId);
//    }
//
//    @PostMapping("/{topicId}/result")
//    public String showResult(@PathVariable Long topicId,
//                             @RequestParam("userAnswers") String userAnswersJson,
//                             Model model) throws Exception {
//        List<QuestionExamDto> questions = examService.getAllExamQuestions(topicId);
//        List<AnswerSubmitRequest> userAnswers = objectMapper.readValue(userAnswersJson,
//                objectMapper.getTypeFactory().constructCollectionType(List.class, AnswerSubmitRequest.class));
//
//        int score = examService.checkExamAnswers(userAnswers);
//
//        // User cavablarını map şəklində hazırla (questionId → answerId)
//        Map<Long, Long> userAnswerMap = userAnswers.stream()
//                .collect(Collectors.toMap(AnswerSubmitRequest::getQuestionId, AnswerSubmitRequest::getAnswerId));
//
//        model.addAttribute("questions", questions);
//        model.addAttribute("userAnswers", userAnswerMap);
//        model.addAttribute("score", score);
//        model.addAttribute("totalQuestions", questions.size());
//        model.addAttribute("isExamFinished", true);
//
//        return "participant/exam";
//    }
//}

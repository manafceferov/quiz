package com.jafarov.quiz.controller;

import com.jafarov.quiz.dto.ParticipantQuestionAnswer;
import com.jafarov.quiz.dto.exam.QuestionExamDto;
import com.jafarov.quiz.dto.examdetail.ParticipantQuizResultDetail;
import com.jafarov.quiz.entity.QuizResult;
import com.jafarov.quiz.util.session.AuthSessionData;
import com.jafarov.quiz.util.session.ParticipantSessionData;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import com.jafarov.quiz.dto.paticipantquiz.ParticipantQuizResultList;
import com.jafarov.quiz.dto.paticipantquiz.QuizResultInsertRequest;
import com.jafarov.quiz.service.QuizResultService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/quiz")
public class QuizResultController extends BaseController {

    private final QuizResultService quizResultService;
    private final AuthSessionData authSessionData;

    public QuizResultController(QuizResultService quizResultService,
                                 AuthSessionData authSessionData
    ) {
        this.quizResultService = quizResultService;
        this.authSessionData = authSessionData;
    }

//    @PostMapping("")
//    public String create(@ModelAttribute QuizResultInsertRequest request,
//                         RedirectAttributes redirectAttributes) {
//
//        // ParticipantId sessiyadan alaraq saveQuizResult çağır
//        ParticipantQuizResultList savedResult = quizResultService.saveQuizResult(
//                request.getTopicId(),
//                request.getAnswers().stream()
//                        .map(a -> new ParticipantQuestionAnswer(a.getQuestionId(), a.getAnswerId()))
//                        .collect(Collectors.toList())
//        );
//
//        redirectAttributes.addFlashAttribute("message", "Quiz result created successfully");
//        return "redirect:/participant/my-exams";
//    }


    @GetMapping("/my-exams")
    public String myExams(Model model) {
        List<ParticipantQuizResultList> results = quizResultService.getResultsForCurrentParticipant();
        model.addAttribute("results", results);
        return "participant/my-exams";
    }

    @GetMapping("/result/{id}")
    public String showResult(@PathVariable Long id,
                             Model model
    ) {
        ParticipantQuizResultList result = quizResultService.showResult(id);
        model.addAttribute("result", result);
        return "participant/result";
    }

    @GetMapping("/{topicId}/question/{questionIndex}")
    public String showQuizWithIndex(@PathVariable Long topicId,
                                    @PathVariable int questionIndex,
                                    Model model
    ) {
        List<QuestionExamDto> questions = quizResultService.getAllExamQuestions(topicId);
        if (questionIndex < 0 || questionIndex >= questions.size()) {
            return "redirect:/participant/" + topicId + "/question/0";
        }

        QuestionExamDto question = questions.get(questionIndex);

        model.addAttribute("question", question);
        model.addAttribute("questions", questions);
        model.addAttribute("topicId", topicId);
        model.addAttribute("questionIndex", questionIndex);
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("isExamFinished", false);

        return "participant/exam";
    }

    @GetMapping("/{topicId}/question")
    public String showQuiz(@PathVariable Long topicId,
                           Model model,
                           HttpSession session
    ) {
        session.setAttribute("topicId", topicId);
        List<QuestionExamDto> questions = quizResultService.getAllExamQuestions(topicId);
        if (questions.isEmpty()) {
            return "redirect:/";
        }
        QuestionExamDto question = questions.get(0);

        model.addAttribute("question", question);
        model.addAttribute("questions", questions);
        model.addAttribute("topicId", topicId);
        model.addAttribute("questionIndex", 0);
        model.addAttribute("totalQuestions", questions.size());
        model.addAttribute("isExamFinished", false);

        return "participant/exam";
    }

    @PostMapping("/{topicId}/result")
    @ResponseBody
    public Long saveResult(@PathVariable Long topicId,
                           @RequestBody QuizResultInsertRequest request
    ) {
        Long participantId = authSessionData.getParticipantSessionData().getId();
        request.setParticipantId(participantId);
        request.setTopicId(topicId);

        QuizResult savedResult = quizResultService.saveQuizResult(request);
        return savedResult.getId();
    }

    @GetMapping("/participant/exam-detail/{quizResultId}")
    public String examDetail(@PathVariable Long quizResultId,
                             Model model
    ) {
        ParticipantQuizResultDetail detail = quizResultService.getExamDetail(quizResultId);
        model.addAttribute("detail", detail);
        return "participant/examdetail";
    }

}
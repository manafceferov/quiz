package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.exam.AnswerExamDto;
import com.jafarov.quiz.dto.exam.AnswerSubmitRequest;
import com.jafarov.quiz.dto.exam.QuestionExamDto;
import com.jafarov.quiz.entity.Answer;
import com.jafarov.quiz.entity.Question;
import com.jafarov.quiz.mapper.QuestionMapper;
import com.jafarov.quiz.repository.QuestionRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final AnswerService answerService;

    public ExamService(QuestionRepository questionRepository,
                       QuestionMapper questionMapper,
                       AnswerService answerService) {
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
        this.answerService = answerService;
    }

    @Transactional(readOnly = true)
    public List<QuestionExamDto> getAllExamQuestions(Long topicId) {
        Pageable pageable = Pageable.unpaged();
        List<Question> questions = questionRepository.findByTopicId(topicId, pageable).getContent();

        List<QuestionExamDto> examDtos = new ArrayList<>();
        for (Question question : questions) {
            if (Boolean.TRUE.equals(question.isActive())) {
                List<Answer> activeAnswers = answerService.getAnswersByQuestionId(question.getId()).stream()
                        .filter(Answer::isActive)
                        .collect(Collectors.toList());

                List<AnswerExamDto> answerDtos = activeAnswers.stream()
                        .map(a -> new AnswerExamDto(a.getId(), a.getAnswer()))
                        .collect(Collectors.toList());

                QuestionExamDto dto = questionMapper.toQuestionExamDtoFromQuestionDbo(question);
                dto.setAnswers(answerDtos);
                examDtos.add(dto);
            }
        }
        return examDtos;
    }

    public int checkExamAnswers(List<AnswerSubmitRequest> submittedAnswers) {
        int correctCount = 0;

        // Sual-ID ilə bir cavab saxlamaq üçün Map istifadə edək
        Map<Long, Long> questionToAnswer = new HashMap<>();

        // Əgər submittedAnswers-da sualın bir neçə cavabı varsa, sonuncusunu saxla
        for (AnswerSubmitRequest submitted : submittedAnswers) {
            questionToAnswer.put(submitted.getQuestionId(), submitted.getAnswerId());
        }

        for (Map.Entry<Long, Long> entry : questionToAnswer.entrySet()) {
            Long questionId = entry.getKey();
            Long answerId = entry.getValue();

            List<Answer> activeCorrectAnswers = answerService.getAnswersByQuestionId(questionId).stream()
                    .filter(a -> a.isActive() && a.isCorrect())
                    .collect(Collectors.toList());

            boolean isCorrect = activeCorrectAnswers.stream()
                    .anyMatch(a -> a.getId().equals(answerId));

            if (isCorrect) {
                correctCount++;
            }
        }
        return correctCount;
    }


    public List<Answer> getActiveAnswersByQuestionId(Long questionId) {
        return answerService.getAnswersByQuestionId(questionId)
                .stream()
                .filter(Answer::isActive)
                .collect(Collectors.toList());
    }

}

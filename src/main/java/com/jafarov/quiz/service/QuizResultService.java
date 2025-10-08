package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.ParticipantQuestionAnswer;
import com.jafarov.quiz.dto.exam.AnswerExamDto;
import com.jafarov.quiz.dto.exam.QuestionExamDto;
import com.jafarov.quiz.dto.paticipantquiz.ParticipantAnswerInsertRequest;
import com.jafarov.quiz.dto.paticipantquiz.ParticipantQuizResultList;
import com.jafarov.quiz.dto.paticipantquiz.QuizResultInsertRequest;
import com.jafarov.quiz.entity.Answer;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.entity.Question;
import com.jafarov.quiz.entity.QuizResult;
import com.jafarov.quiz.mapper.AnswerMapper;
import com.jafarov.quiz.mapper.ParticipantAnswerMapper;
import com.jafarov.quiz.mapper.QuestionMapper;
import com.jafarov.quiz.mapper.QuizResultMapper;
import com.jafarov.quiz.repository.QuestionRepository;
import com.jafarov.quiz.repository.QuizResultRepository;
import com.jafarov.quiz.util.session.ParticipantSessionData;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizResultService {

    private final QuizResultRepository quizResultRepository;
    private final AnswerService answerService;
    private final QuestionRepository questionRepository;
    private final QuizResultMapper quizResultMapper;
    private final ParticipantSessionData participantSessionData;
    private final ParticipantAnswerService participantAnswerService;
    private final ParticipantAnswerMapper participantAnswerMapper;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    public QuizResultService(
            QuizResultRepository quizResultRepository,
            AnswerService answerService,
            QuestionRepository questionRepository,
            QuizResultMapper quizResultMapper,
            ParticipantSessionData participantSessionData,
            ParticipantAnswerService participantAnswerService,
            ParticipantAnswerMapper participantAnswerMapper,
            QuestionMapper questionMapper,
            AnswerMapper answerMapper
    ) {
        this.quizResultRepository = quizResultRepository;
        this.answerService = answerService;
        this.questionRepository = questionRepository;
        this.quizResultMapper = quizResultMapper;
        this.participantSessionData = participantSessionData;
        this.participantAnswerService = participantAnswerService;
        this.participantAnswerMapper = participantAnswerMapper;
        this.questionMapper = questionMapper;
        this.answerMapper = answerMapper;
    }

    @Transactional
    public void saveQuizResult(Long topicId, QuizResultInsertRequest request) {

        Participant participant = participantSessionData.getParticipant();

        List<ParticipantAnswerInsertRequest> answerInserts = request.getAnswers().stream()
                .map(ua -> new ParticipantAnswerInsertRequest(ua.getQuestionId(), ua.getAnswerId(), null))
                .collect(Collectors.toList());
        request.setAnswers(answerInserts);
        request.calculateQuestionCount(); // questionCount update

        long correctCount = answerInserts.stream()
                .filter(a -> answerService.getCorrectAnswersCountByQuestionId(a.getQuestionId(), a.getAnswerId()) > 0)
                .count();
        request.setCorrectAnswersCount(correctCount);
        request.setCorrectPercent(answerInserts.isEmpty() ? 0 : correctCount * 100 / answerInserts.size());

        QuizResult quizResult = new QuizResult();
        quizResult.setParticipantId(participant.getId());
        quizResult.setTopicId(topicId);
        quizResult.setCorrectAnswersCount(request.getCorrectAnswersCount());
        quizResult.setCorrectPercent(request.getCorrectPercent());
        quizResult.setQuestionsCount(request.getQuestionCount());

        QuizResult savedQuizResult = quizResultRepository.saveAndFlush(quizResult);

        answerInserts.forEach(a -> a.setQuizResultId(savedQuizResult.getId()));
        participantAnswerService.saveAll(participantAnswerMapper.toParticipantAnswerList(answerInserts));
        quizResultMapper.toParticipantQuizResultList(savedQuizResult);
    }


    public ParticipantQuizResultList showResult(Long id) {
        QuizResult quizResult = quizResultRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz result not found: " + id));
        return quizResultMapper.toParticipantQuizResultList(quizResult);
    }

    public Long getCurrentParticipantId() {
        return participantSessionData.getId();
    }

    public List<ParticipantQuizResultList> getResultsForCurrentParticipant() {
        Long participantId = getCurrentParticipantId();
        return quizResultRepository.findAllByParticipantId(participantId)
                .stream()
                .map(quizResultMapper::toParticipantQuizResultList)
                .collect(Collectors.toList());
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
                        .map(a -> new AnswerExamDto(a.getId(), a.getAnswer(), a.isCorrect()))
                        .collect(Collectors.toList());

                QuestionExamDto dto = questionMapper.toQuestionExamDtoFromQuestionDbo(question);
                dto.setAnswers(answerDtos);
                examDtos.add(dto);
            }
        }
        return examDtos;
    }

}
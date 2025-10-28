package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.exam.QuestionExamDto;
import com.jafarov.quiz.dto.examdetail.ParticipantAnswerDetail;
import com.jafarov.quiz.dto.examdetail.ParticipantQuestionWithAnswers;
import com.jafarov.quiz.dto.examdetail.ParticipantQuizResultDetail;
import com.jafarov.quiz.dto.paticipantquiz.ParticipantAnswerInsertRequest;
import com.jafarov.quiz.dto.paticipantquiz.ParticipantQuizResultList;
import com.jafarov.quiz.dto.paticipantquiz.QuizResultInsertRequest;
import com.jafarov.quiz.entity.*;
import com.jafarov.quiz.mapper.AnswerMapper;
import com.jafarov.quiz.mapper.ParticipantAnswerMapper;
import com.jafarov.quiz.mapper.QuestionMapper;
import com.jafarov.quiz.mapper.QuizResultMapper;
import com.jafarov.quiz.repository.AnswerRepository;
import com.jafarov.quiz.repository.ParticipantAnswerRepository;
import com.jafarov.quiz.repository.QuestionRepository;
import com.jafarov.quiz.repository.QuizResultRepository;
import com.jafarov.quiz.util.session.AuthSessionData;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizResultService {

    private final QuizResultRepository quizResultRepository;
    private final AnswerService answerService;
    private final QuestionRepository questionRepository;
    private final QuizResultMapper quizResultMapper;
    private final AuthSessionData authSessionData;
    private final ParticipantAnswerService participantAnswerService;
    private final ParticipantAnswerMapper participantAnswerMapper;
    private final ParticipantAnswerRepository participantAnswerRepository;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final AnswerRepository answerRepository;

    public QuizResultService(QuizResultRepository quizResultRepository,
                             AnswerService answerService,
                             QuestionRepository questionRepository,
                             QuizResultMapper quizResultMapper,
                             AuthSessionData authSessionData,
                             ParticipantAnswerService participantAnswerService,
                             ParticipantAnswerMapper participantAnswerMapper,
                             ParticipantAnswerRepository participantAnswerRepository,
                             QuestionMapper questionMapper,
                             AnswerMapper answerMapper,
                             AnswerRepository answerRepository
    ) {
        this.quizResultRepository = quizResultRepository;
        this.answerService = answerService;
        this.questionRepository = questionRepository;
        this.quizResultMapper = quizResultMapper;
        this.authSessionData = authSessionData;
        this.participantAnswerService = participantAnswerService;
        this.participantAnswerMapper = participantAnswerMapper;
        this.participantAnswerRepository = participantAnswerRepository;
        this.questionMapper = questionMapper;
        this.answerMapper = answerMapper;
        this.answerRepository = answerRepository;
    }

    @Transactional
    public QuizResult saveQuizResult(QuizResultInsertRequest request) {
        if (request.getParticipantId() == null) {
            request.setParticipantId(authSessionData.getParticipantSessionData().getId());
        }
        Long topicId = request.getTopicId();
        long totalQuestions = questionRepository.getCountByTopicId(topicId);
        long correctCount = 0;

        List<Answer> correctAnswers = answerRepository.findCorrectAnswersByTopicId(topicId);

        Map<Long, Long> correctAnswerMap = new HashMap<>();
        for (Answer correctAnswer : correctAnswers) {
            correctAnswerMap.put(correctAnswer.getQuestion().getId(), correctAnswer.getId());
        }

        Map<Long, ParticipantAnswerInsertRequest> processedAnswers = new HashMap<>();
        for (ParticipantAnswerInsertRequest answerRequest : request.getAnswers()) {
            Long questionId = answerRequest.getQuestionId();
            Long answerId = answerRequest.getAnswerId();
            if (questionId != null && questionRepository.existsById(questionId)
                    && questionRepository.findById(questionId).get().getTopicId().equals(topicId)) {
                processedAnswers.put(questionId, answerRequest);
            }
        }

        for (ParticipantAnswerInsertRequest answerRequest : processedAnswers.values()) {
            Long questionId = answerRequest.getQuestionId();
            Long answerId = answerRequest.getAnswerId();
            Long correctAnswerId = correctAnswerMap.get(questionId);
            if (correctAnswerId != null && correctAnswerId.equals(answerId)) {
                correctCount++;
            }
        }

        long percent = totalQuestions == 0 ? 0 : Math.round((double) correctCount / totalQuestions * 100);
        request.setQuestionsCount(totalQuestions);
        request.setCorrectAnswersCount(correctCount);
        request.setCorrectPercent(percent);
        QuizResult result = quizResultMapper.toDbo(request);
        QuizResult savedResult = quizResultRepository.save(result);

        List<ParticipantAnswer> answersToSave = new ArrayList<>();
        for (ParticipantAnswerInsertRequest answerRequest : request.getAnswers()) {
            ParticipantAnswer answer = new ParticipantAnswer();
            answer.setQuizResultId(savedResult.getId());
            answer.setQuestionId(answerRequest.getQuestionId());
            answer.setAnswerId(answerRequest.getAnswerId());
            answersToSave.add(answer);
        }
        participantAnswerService.saveAll(answersToSave);
        return savedResult;
    }

    public ParticipantQuizResultList showResult(Long id) {
        QuizResult quizResult = quizResultRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Quiz result not found: " + id));
        return quizResultMapper.toParticipantQuizResultList(quizResult);
    }

    public Long getCurrentParticipantId() {
        return authSessionData.getParticipantSessionData().getId();
    }

    public List<ParticipantQuizResultList> getResultsForCurrentParticipant() {
        Long participantId = getCurrentParticipantId();
        return quizResultRepository.findAllByParticipantId(participantId).stream()
                .map(quizResultMapper::toParticipantQuizResultList)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<QuestionExamDto> getAllExamQuestions(Long topicId) {
        return questionRepository.findByTopicId(topicId, Pageable.unpaged())
                .getContent().stream()
                .filter(q -> Boolean.TRUE.equals(q.isActive()))
                .map(q -> {
                    QuestionExamDto dto = questionMapper.toQuestionExamDtoFromQuestionDbo(q);
                    List<Answer> activeAnswers = answerService.getAnswersByQuestionId(q.getId())
                            .stream().filter(Answer::isActive).toList();
                    dto.setAnswers(answerMapper.toAnswerExamDtoList(activeAnswers));
                    return dto;
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public ParticipantQuizResultDetail getExamDetail(Long quizResultId) {
        QuizResult quizResult = quizResultRepository.findById(quizResultId)
                .orElseThrow(() -> new IllegalArgumentException("Quiz result not found: " + quizResultId));

        ParticipantQuizResultDetail detail = new ParticipantQuizResultDetail();
        detail.setParticipantId(quizResult.getParticipantId());
        detail.setTopicName(quizResult.getTopic().getName());
        detail.setCorrectAnswersCount(quizResult.getCorrectAnswersCount());
        detail.setQuestionCount(quizResult.getQuestionsCount());
        detail.setCorrectPercent(quizResult.getCorrectPercent());

        List<ParticipantAnswer> participantAnswers = participantAnswerRepository.findByQuizResultId(quizResultId);

        Map<Question, List<ParticipantAnswer>> groupByQuestion = participantAnswers.stream()
                .collect(Collectors.groupingBy(ParticipantAnswer::getQuestion,
                        LinkedHashMap::new, Collectors.toList()));

        List<ParticipantQuestionWithAnswers> questionDTOs = groupByQuestion.entrySet().stream()
                .map(entry -> {
                    Question question = entry.getKey();
                    List<ParticipantAnswer> answersForQuestion = entry.getValue();

                    ParticipantQuestionWithAnswers participantQuestionWithAnswers = new ParticipantQuestionWithAnswers();
                    participantQuestionWithAnswers.setQuestion(question.getQuestion());

                    List<Answer> allAnswers = question.getAnswers().stream()
                            .filter(Answer::isActive)
                            .toList();

                    List<ParticipantAnswerDetail> answerDetails = allAnswers.stream()
                            .map(answer -> {
                                boolean selected = answersForQuestion.stream()
                                        .anyMatch(pa -> pa.getAnswerId() != null && pa.getAnswerId().equals(answer.getId()));
                                ParticipantAnswerDetail participantAnswerDetail = new ParticipantAnswerDetail();
                                participantAnswerDetail.setAnswer(answer.getAnswer());
                                participantAnswerDetail.setCorrect(answer.isCorrect());
                                participantAnswerDetail.setSelected(selected);
                                return participantAnswerDetail;
                            })
                            .toList();
                    participantQuestionWithAnswers.setAnswers(answerDetails);
                    return participantQuestionWithAnswers;
                })
                .toList();
        detail.setQuestions(questionDTOs);
        return detail;
    }
}

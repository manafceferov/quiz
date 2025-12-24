package com.quiz.service;

import com.quiz.dto.answer.AnswerInsertRequest;
import com.quiz.dto.answer.AnswerUpdateRequest;
import com.quiz.dto.question.QuestionEditDto;
import com.quiz.dto.question.QuestionInsertRequest;
import com.quiz.dto.question.QuestionUpdateRequest;
import com.quiz.entity.Question;
import com.quiz.mapper.QuestionMapper;
import com.quiz.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class QuestionService {

    private final QuestionRepository repository;
    private final QuestionMapper mapper;
    private final AnswerService answerService;

    public QuestionService(QuestionRepository repository,
                           QuestionMapper mapper,
                           AnswerService answerService
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.answerService = answerService;
    }

    @Transactional
    public void save(QuestionInsertRequest request,
                     int correctAnswerIndex
    ) {
        Question question = repository.saveAndFlush(mapper.toDboQuestionFromQuestionInsertRequest(request));
        Objects.requireNonNull(request.getAnswers()).forEach(
                x -> {
                    x.setQuestionId(question.getId());
                    x.setCorrect(correctAnswerIndex == request.getAnswers().indexOf(x));
                });
        answerService.saveAll(request.getAnswers());
    }

    public QuestionEditDto getById(Long id) {
        Question entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sual tapılmadı: " + id));
        return mapper.toQuestionEditDtoFromQuestionDbo(entity);
    }

    public Page<Question> getQuestionsByTopicId(Long topicId,
                                                Pageable pageable
    ) {
        return repository.findByTopicId(topicId, pageable);
    }

    @Transactional
    public void update(QuestionUpdateRequest request,
                       int correctAnswerIndex
    ) {
        Question question = repository.findById(Objects.requireNonNull(request.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Sual tapılmadı: " + request.getId()));
        question.setQuestion(request.getQuestion());
        question.setTopicId(request.getTopicId());
        repository.save(question);

        for (int i = 0; i < request.getAnswers().size(); i++) {
            AnswerUpdateRequest answer = request.getAnswers().get(i);
            answer.setIsCorrect(i == correctAnswerIndex);
        }
        List<Long> existingAnswerIds = new ArrayList<>();
        List<AnswerUpdateRequest> toUpdate = new ArrayList<>();
        List<AnswerInsertRequest> toInsert = new ArrayList<>();
        for (AnswerUpdateRequest answer : request.getAnswers()) {
            if (answer.getId() != null) {
                existingAnswerIds.add(answer.getId());
                toUpdate.add(answer);
            } else {
                AnswerInsertRequest insertRequest = new AnswerInsertRequest();
                insertRequest.setQuestionId(question.getId());
                insertRequest.setAnswer(answer.getAnswer());
                toInsert.add(insertRequest);
            }
        }
        answerService.deleteByIds(question.getId(), existingAnswerIds);
        answerService.updateAll(toUpdate);
        answerService.saveAll(toInsert);
        changeStatus(question.getId(), true);
    }

    public void chechkQuestionStatus(Long id) {
        repository.deactivateIfAnyAnswerIsInactive(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Question> findById(Long id) {
        return repository.findById(id);
    }

    public Page<Question> searchQuestionsByTopicAndKeyword(Long topicId,
                                                           String keyword,
                                                           Pageable pageable
    ) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return repository.findByTopicId(topicId, pageable);
        }
        return repository.findByTopicIdAndQuestionContainingIgnoreCase(topicId, keyword.trim(), pageable);
    }

    public QuestionEditDto getQuestionWithAnswersById(Long id) {
        Question data = repository.getQuestionWithAnswersById(id);
        return mapper.toQuestionEditDtoFromQuestionDbo(data);
    }

    @Transactional
    public Boolean changeStatus(Long id,
                                Boolean status
    ) {
        Boolean active = answerService.getActiveAnswerByQuestionId(id);
        if (!active && status) {
            return false;
        }

        repository.changeStatus(id, status);
        return true;
    }

    public Page<QuestionEditDto> getQuestionsWithParticipantByTopic(Long topicId,
                                                                    String keyword,
                                                                    Pageable pageable
    ) {
        Page<Question> questions;
        if (keyword == null || keyword.trim().isEmpty()) {
            questions = repository.findByTopicId(topicId, pageable);
        } else {
            questions = repository.findByTopicIdAndQuestionContainingIgnoreCase(topicId, keyword.trim(), pageable);
        }
        return questions.map(mapper::toQuestionEditDtoFromQuestionDbo);
    }


    public Long getQuestionCountByTopicId(Long topicId) {
        return repository.getCountByTopicId(topicId);
    }
}

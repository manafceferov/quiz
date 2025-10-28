package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.answer.AnswerInsertRequest;
import com.jafarov.quiz.dto.answer.AnswerUpdateRequest;
import com.jafarov.quiz.dto.answer.AnswerEditDto;
import com.jafarov.quiz.entity.Answer;
import com.jafarov.quiz.mapper.AnswerMapper;
import com.jafarov.quiz.mapper.QuestionMapper;
import com.jafarov.quiz.repository.AnswerRepository;
import com.jafarov.quiz.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository repository;
    private final AnswerMapper mapper;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;


    public AnswerService(AnswerRepository repository,
                         AnswerMapper mapper,
                         QuestionRepository questionRepository,
                         QuestionMapper questionMapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    public List<Answer> getAnswersByQuestionId(Long questionId) {

        return repository.findByQuestionId(questionId);
    }

    @Transactional
    public void save(AnswerInsertRequest request) {
        Answer answer = mapper.toDboFromInsert(request);
        repository.save(answer);
    }

    @Transactional
    public void saveAll(List<AnswerInsertRequest> requestList) {
        repository.saveAll(mapper.toDboFromAnswerInsertRequest(requestList));
    }

    @Transactional
    public void updateAll(List<AnswerUpdateRequest> request) {
        repository.saveAll(mapper.toDboFromAnswerUpdateRequest(request));
    }

    public AnswerEditDto getById(Long id) {
        Answer answer = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cavab tapılmadı: " + id));
        return mapper.toDto(answer);
    }

    public void deleteById(Long id,
                           List<Long> answerDeleteIds
    ) {
        repository.deleteByIds(id, answerDeleteIds);

    }

    @Transactional
    public void deleteByIds(Long questionId,
                            List<Long> ids
    ) {
        repository.deleteByIds(questionId, ids);
    }

    @Transactional
    public void changeStatus(Long id,
                             Boolean status
    ) {
        repository.changeStatus(id, status);
    }

    public Boolean getActiveAnswerByQuestionId(Long questionId) {
        return repository.getExsistTwoIsActiveAnswerByQuestionId(questionId);
    }

}


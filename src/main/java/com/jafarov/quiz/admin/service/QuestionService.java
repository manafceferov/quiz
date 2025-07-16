package com.jafarov.quiz.admin.service;

import com.jafarov.quiz.admin.dto.question.QuestionEditDto;
import com.jafarov.quiz.admin.dto.question.QuestionInsertRequest;
import com.jafarov.quiz.admin.dto.question.QuestionUpdateRequest;
import com.jafarov.quiz.admin.entity.Question;
import com.jafarov.quiz.admin.mapper.QuestionMapper;
import com.jafarov.quiz.admin.repository.QuestionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository repository;
    private final QuestionMapper mapper;

    public QuestionService(QuestionRepository repository, QuestionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public void save(QuestionInsertRequest request) {
        repository.save(mapper.toDboQuestionFromQuestionInsertRequest(request));
    }

    public QuestionEditDto getById(Long id) {
        Question entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sual tapılmadı: " + id));
        return mapper.toDto(entity);
    }

    public Page<Question> getQuestionsByTopicId(Long topicId, Pageable pageable) {

        return repository.findByTopicId(topicId, pageable);
    }

    @Transactional
    public void update(QuestionUpdateRequest request) {
        Question updatedQuestion = mapper.toDboQuestionFromQuestionUpdateRequest(request);
        repository.save(updatedQuestion);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Question> findById(Long id) {
        return repository.findById(id);
    }

    public Page<Question> searchQuestionsByTopicAndKeyword(Long topicId, String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return repository.findByTopicId(topicId, pageable);
        }
        return repository.findByTopicIdAndQuestionContainingIgnoreCase(topicId, keyword.trim(), pageable);
    }

}

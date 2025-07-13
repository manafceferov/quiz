package com.jafarov.quiz.admin.service;

import com.jafarov.quiz.admin.dto.topic.QuizEditDto;
import com.jafarov.quiz.admin.dto.topic.QuizTopicInsertRequest;
import com.jafarov.quiz.admin.dto.topic.QuizTopicUpdateRequest;
import com.jafarov.quiz.admin.entity.Topic;
import com.jafarov.quiz.admin.mapper.QuizTopicMapper;
import com.jafarov.quiz.admin.repository.QuizTopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class QuizTopicService {

    private final QuizTopicRepository repository;
    private final QuizTopicMapper mapper;

    public QuizTopicService(QuizTopicRepository repository, QuizTopicMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public void save(QuizTopicInsertRequest request) {
        repository.save(mapper.toDboQuizTopicFromQuizTopicInsertRequest(request));
    }

    public QuizEditDto getById(Long id) {
        Topic entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mövzu tapılmadı: " + id));
        return mapper.toDto(entity);
    }

    public Page<Topic> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public void update(QuizTopicUpdateRequest request) {
        Topic updatedTopic = mapper.toDboQuizTopicFromQuizTopicUpdateRequest(request);
        repository.save(updatedTopic);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Optional<Topic> findById(Long id) {
        return repository.findById(id);
    }
}

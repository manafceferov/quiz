package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.topic.*;
import com.jafarov.quiz.entity.Topic;
import com.jafarov.quiz.mapper.TopicMapper;
import com.jafarov.quiz.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository repository;
    private final TopicMapper mapper;

    public TopicService(TopicRepository repository,
                        TopicMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    public void save(TopicInsertRequest request) {
        repository.save(mapper.toDboQuizTopicFromQuizTopicInsertRequest(request));
    }

    public TopicEditDto getById(Long id) {
        Topic entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mövzu tapılmadı: " + id));
        return mapper.toDto(entity);
    }

    public Page<Topic> getAll(Pageable pageable) {

        return repository.findAll(pageable);
    }

    public Page<TopicWithQuestionCountProjection> getAllTopics(Pageable pageable) {
        return repository.getAllTopicsWithQuestionsCount(pageable);
    }

    @Transactional
    public void update(TopicUpdateRequest request) {
        Topic updatedTopic = mapper.toDboQuizTopicFromQuizTopicUpdateRequest(request);
        repository.save(updatedTopic);
    }

    public void deleteById(Long id) {

        repository.deleteById(id);
    }

    public Optional<Topic> findById(Long id) {

        return repository.findById(id);
    }

    public Page<Topic> getAll(String name, Pageable pageable) {
        if (name == null || name.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.findByNameContainingIgnoreCase(name.trim(), pageable);
    }

    @Transactional
    public void changeStatus(Long id, Boolean status) {
        repository.changeStatus(id, status);
    }
}

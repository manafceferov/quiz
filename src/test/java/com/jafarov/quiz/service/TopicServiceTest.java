package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.topic.TopicEditDto;
import com.jafarov.quiz.dto.topic.TopicInsertRequest;
import com.jafarov.quiz.dto.topic.TopicUpdateRequest;
import com.jafarov.quiz.entity.Topic;
import com.jafarov.quiz.mapper.TopicMapper;
import com.jafarov.quiz.repository.AnswerRepository;
import com.jafarov.quiz.repository.QuestionRepository;
import com.jafarov.quiz.repository.TopicRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TopicServiceTest {

    private TopicRepository topicRepository;
    private TopicMapper topicMapper;
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private QuestionService questionService;

    private TopicService topicService;

    @BeforeEach
    void setUp() {
        topicRepository = mock(TopicRepository.class);
        topicMapper = mock(TopicMapper.class);
        questionRepository = mock(QuestionRepository.class);
        answerRepository = mock(AnswerRepository.class);
        questionService = mock(QuestionService.class);

        topicService = new TopicService(
                topicRepository,
                topicMapper,
                questionRepository,
                answerRepository,
                questionService
        );
    }

    @Test
    void testSave() {
        TopicInsertRequest request = new TopicInsertRequest();
        Topic topic = new Topic();
        when(topicMapper.toDboQuizTopicFromQuizTopicInsertRequest(request)).thenReturn(topic);

        topicService.save(request);
        verify(topicRepository, times(1)).save(topic);
    }

    @Test
    void testGetById_found() {
        Topic topic = new Topic();
        TopicEditDto dto = new TopicEditDto();
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));
        when(topicMapper.toDto(topic)).thenReturn(dto);

        TopicEditDto result = topicService.getById(1L);
        assertNotNull(result);
        assertEquals(dto, result);
    }

    @Test
    void testGetById_notFound() {
        when(topicRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> topicService.getById(1L));
    }

    @Test
    void testGetAll() {
        Topic topic = new Topic();
        Page<Topic> page = new PageImpl<>(Collections.singletonList(topic));
        when(topicRepository.findAll(Pageable.unpaged())).thenReturn(page);

        Page<Topic> result = topicService.getAll(Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testUpdate() {
        TopicUpdateRequest request = new TopicUpdateRequest();
        Topic topic = new Topic();
        when(topicMapper.toDboQuizTopicFromQuizTopicUpdateRequest(request)).thenReturn(topic);

        topicService.update(request);
        verify(topicRepository, times(1)).save(topic);
    }

    @Test
    void testDeleteById() {
        topicService.deleteById(1L);
        verify(topicRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindById() {
        Topic topic = new Topic();
        when(topicRepository.findById(1L)).thenReturn(Optional.of(topic));

        Optional<Topic> result = topicService.findById(1L);
        assertTrue(result.isPresent());
        assertEquals(topic, result.get());
    }

    @Test
    void testGetAll_withName() {
        Topic topic = new Topic();
        Page<Topic> page = new PageImpl<>(Collections.singletonList(topic));
        when(topicRepository.findByNameContainingIgnoreCase("math", Pageable.unpaged())).thenReturn(page);

        Page<Topic> result = topicService.getAll("math", Pageable.unpaged());
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void testChangeStatus_deactivate() {
        topicService.changeStatus(1L, false);
        verify(topicRepository, times(1)).changeStatus(1L, false);
        verify(questionRepository, times(1)).deactivateByTopicId(1L);
        verify(answerRepository, times(1)).deactivateByTopicId(1L);
    }

    @Test
    void testChangeStatus_activate() {
        topicService.changeStatus(1L, true);
        verify(topicRepository, times(1)).changeStatus(1L, true);
        verify(questionRepository, never()).deactivateByTopicId(anyLong());
        verify(answerRepository, never()).deactivateByTopicId(anyLong());
    }
}

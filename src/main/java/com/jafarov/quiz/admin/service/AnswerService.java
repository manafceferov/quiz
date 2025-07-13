package com.jafarov.quiz.admin.service;

import com.jafarov.quiz.admin.repository.AnswerRepository;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {

    private final AnswerRepository repository;

    public AnswerService(AnswerRepository repository) {
        this.repository = repository;
    }


}

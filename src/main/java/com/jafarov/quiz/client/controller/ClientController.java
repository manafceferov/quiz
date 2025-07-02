package com.jafarov.quiz.client.controller;

import ch.qos.logback.core.model.Model;
import com.jafarov.quiz.client.dto.ClientDto;
import com.jafarov.quiz.client.service.ClientService;
import com.jafarov.quiz.entity.Question;
import com.jafarov.quiz.entity.QuizEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client/quizzes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientDto> getAll() {
        return clientService.getAvailableQuizzes()
                .stream()
                .map(quiz -> {
                    ClientDto dto = new ClientDto();
                    dto.setId(quiz.getId());
                    dto.setTitle(quiz.getTitle());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ClientDto getById(@PathVariable Long id) {
        QuizEntity quiz = clientService.getQuizById(id);
        if (quiz == null) {
            return null; // Ya da ResponseEntity.notFound() istifadə edə bilərsən
        }
        ClientDto dto = new ClientDto();
        dto.setId(quiz.getId());
        dto.setTitle(quiz.getTitle());
        return dto;
    }
}
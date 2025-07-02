package com.jafarov.quiz.admin.controller;

import com.jafarov.quiz.admin.dto.AdminRequestDto;
import com.jafarov.quiz.admin.dto.AdminRequestDto;
import com.jafarov.quiz.admin.dto.AdminResponseDto;
import com.jafarov.quiz.admin.service.AdminService;
import com.jafarov.quiz.entity.QuizEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/quizzes")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    public List<AdminResponseDto> getAll() {
        return adminService.getAllQuizzes()
                .stream()
                .map(quiz -> {
                    AdminResponseDto dto = new AdminResponseDto();
                    dto.setId(quiz.getId());
                    dto.setTitle(quiz.getTitle());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody AdminRequestDto dto) {
        QuizEntity quiz = new QuizEntity();
        quiz.setTitle(dto.getTitle());
        adminService.createQuiz(quiz);
        return ResponseEntity.ok("Quiz yaradıldı");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        adminService.deleteQuiz(id);
        return ResponseEntity.ok("Quiz silindi");
    }
}

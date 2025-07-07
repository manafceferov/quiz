package com.jafarov.quiz.admin.service;

import com.jafarov.quiz.admin.dto.user.UserInsertRequest;
import com.jafarov.quiz.admin.dto.user.UserUpdateRequest;
import com.jafarov.quiz.admin.entity.User;
import com.jafarov.quiz.admin.mapper.UserMapper;
import com.jafarov.quiz.admin.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentService attachmentService;

    public UserService(UserRepository repository,
                       UserMapper mapper,
                       PasswordEncoder passwordEncoder, AttachmentService attachmentService
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.attachmentService = attachmentService;
    }

    public void save(UserInsertRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = repository.save(mapper.toDboUserFromUserInsertRequest(request));

        attachmentService.upload(user.getId(),request.getFile());
    }

    public Page<User> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public User getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void update(UserUpdateRequest request) {
        User user = repository.findById(request.getId()).orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı: " + request.getId()));

        if(request.getPassword() == null || request.getPassword().isBlank()) {
            request.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        repository.save(mapper.toDboUserFromUserUpdateRequest(request));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

}

package com.jafarov.quiz.admin.service;

import com.jafarov.quiz.admin.dto.user.UserIUDRequest;
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

    public UserService(UserRepository repository,
                       UserMapper mapper,
                       PasswordEncoder passwordEncoder
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(UserIUDRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(mapper.toDboUserFromUserIUDRequest(request));
    }

    public Page<User> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public User getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void update(UserIUDRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        repository.save(mapper.toDboUserFromUserIUDRequest(request));
    }

}

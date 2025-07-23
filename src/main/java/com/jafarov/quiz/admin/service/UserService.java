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

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentService attachmentService;

    public UserService(UserRepository repository,
                       UserMapper mapper,
                       PasswordEncoder passwordEncoder,
                       AttachmentService attachmentService) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.attachmentService = attachmentService;
    }

    public void save(UserInsertRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = mapper.toDboUserFromUserInsertRequest(request);
        user = repository.save(user); // save first to get ID

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            attachmentService.upload(user.getId(), request.getFile());
        }
    }

    public void saveAll(List<UserInsertRequest> request) {
        request.forEach(x ->
                x.setPassword(passwordEncoder.encode(x.getPassword()))
        );

        List<User> user = mapper.toDboUserFromUserInsertRequest(request);
        user = repository.saveAll(user);
    }

    public Page<User> getAll(Pageable pageable) {

        return repository.findAll(pageable);
    }

    public User edit(Long id) {

        return repository.getUserByIdWithAttachment(id);
    }

    public void update(UserUpdateRequest request) {
        User existingUser = repository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("User not found: " + request.getId()));

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            request.setPassword(existingUser.getPassword());
        } else {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getFile() != null && !request.getFile().isEmpty()) {
            attachmentService.deleteByUserId(existingUser.getId());
            attachmentService.upload(existingUser.getId(), request.getFile());
        }

        User updatedUser = mapper.toDboUserFromUserUpdateRequest(request);
        updatedUser.setId(existingUser.getId());
        updatedUser.setAttachment(existingUser.getAttachment());

        repository.save(updatedUser);
    }

    public void deleteById(Long id) {
        attachmentService.deleteByUserId(id);
        repository.deleteById(id);
    }

    public User findById(Long id) {

        return repository.findById(id).orElse(null);
    }

    public User findByEmail(String username) {

        return repository.findByEmail(username).orElse(null);
    }

    public Page<User> searchUsers(String name, Pageable pageable) {
        if (name != null && !name.trim().isEmpty()) {
            return repository.searchByFullName(name.trim(), pageable);
        }
        return repository.findAll(pageable);
    }

}

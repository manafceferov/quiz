package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.user.UserInsertRequest;
import com.jafarov.quiz.dto.user.UserUpdateRequest;
import com.jafarov.quiz.entity.User;
import com.jafarov.quiz.mapper.UserMapper;
import com.jafarov.quiz.repository.UserRepository;
import com.jafarov.quiz.enums.OwnerType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder ;
    private final AttachmentService attachmentService;

    public UserService(UserRepository repository,
                       UserMapper mapper,
                       AttachmentService attachmentService,
                       PasswordEncoder passwordEncoder
    ) {
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
            attachmentService.upload(user.getId(), OwnerType.USER, request.getFile());
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
            attachmentService.deleteByOwner(existingUser.getId(), OwnerType.USER);
            attachmentService.upload(existingUser.getId(), OwnerType.USER, request.getFile());
        }

        User updatedUser = mapper.toDboUserFromUserUpdateRequest(request);
        updatedUser.setId(existingUser.getId());
        updatedUser.setAttachment(existingUser.getAttachment());

        repository.save(updatedUser);
    }

    public void deleteById(Long id) {
        attachmentService.deleteByOwner(id, OwnerType.USER);
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

    @Transactional
    public void changeStatus(Long id, Boolean status) {
        repository.changeStatus(id, status);
    }

}

package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.admin.AdminInsertRequest;
import com.jafarov.quiz.dto.admin.AdminUpdateRequest;
import com.jafarov.quiz.entity.Admin;
import com.jafarov.quiz.entity.Attachment;
import com.jafarov.quiz.enums.OwnerType;
import com.jafarov.quiz.mapper.AdminMapper;
import com.jafarov.quiz.repository.AdminRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class AdminService {

    private final AdminRepository repository;
    private final AdminMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentService attachmentService;

    public AdminService(AdminRepository repository,
                        AdminMapper mapper,
                        PasswordEncoder passwordEncoder,
                        AttachmentService attachmentService
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.attachmentService = attachmentService;
    }

    public Page<Admin> searchUsers(String name,
                                   Pageable pageable
    ) {
        if (name == null || name.isBlank()) {
            return repository.findAll(pageable);
        }
        return repository.searchByFullName(name, pageable);
    }

    public void save(AdminInsertRequest request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        Admin admin = repository.save(mapper.toDboUserFromUserInsertRequest(request));

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            Attachment attachment = attachmentService.uploadAndReturn(admin.getId(), OwnerType.USER, request.getFile());
            attachment.setAdmin(admin);
            admin.setAttachment(attachment);
            repository.save(admin);
        }
    }

    public void update(AdminUpdateRequest request) {
        Admin admin = repository.findById(Objects.requireNonNull(request.getId()))
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı: " + request.getId()));

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            request.setPassword(admin.getPassword());
        } else {
            request.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Admin updated = mapper.toDboUserFromUserUpdateRequest(request);
        updated.setId(admin.getId());

        if (request.getFile() != null && !request.getFile().isEmpty()) {
            Attachment attachment = attachmentService.uploadAndReturn(admin.getId(), OwnerType.USER, request.getFile());
            attachment.setAdmin(updated);
            updated.setAttachment(attachment);
        }
        repository.save(updated);
    }

    @Transactional
    public void changeStatus(Long id,
                             Boolean status
    ) {
        repository.changeStatus(id, status);
    }

    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public void deleteById(Long id) {
        attachmentService.deleteByOwner(id, OwnerType.USER);
        repository.deleteById(id);
    }

    public void saveAll(List<AdminInsertRequest> requests) {
        for (AdminInsertRequest request : requests) {
            save(request);
        }
    }

    public Admin edit(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı: " + id));
    }
}

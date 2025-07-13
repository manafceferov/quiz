package com.jafarov.quiz.admin.service;

import com.jafarov.quiz.admin.entity.Attachment;
import com.jafarov.quiz.admin.entity.User;
import com.jafarov.quiz.admin.repository.AttachmentRepository;
import com.jafarov.quiz.admin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class AttachmentService {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, UserRepository userRepository) {
        this.attachmentRepository = attachmentRepository;
        this.userRepository = userRepository;
    }

    public void upload(Long userId, MultipartFile file) {
        if (file.isEmpty()) return;

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);

            String fileUrl = "/uploads/" + fileName;

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

            // Köhnə faylı sil
            Attachment existingAttachment = attachmentRepository.findByUserId(userId);
            if (existingAttachment != null) {
                deleteFile(existingAttachment.getFileUrl());
                attachmentRepository.delete(existingAttachment);
            }

            Attachment attachment = new Attachment(fileName, fileUrl, LocalDateTime.now());
            attachmentRepository.save(attachment);

        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    public void deleteByUserId(Long userId) {
        Attachment attachment = attachmentRepository.findByUserId(userId);
        if (attachment != null) {
            deleteFile(attachment.getFileUrl());
            attachmentRepository.delete(attachment);
        }
    }

    private void deleteFile(String fileUrl) {
        File file = new File("src/main/resources/static" + fileUrl);
        if (file.exists()) {
            file.delete();
        }
    }
}
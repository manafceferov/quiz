package com.jafarov.quiz.service;

import com.jafarov.quiz.entity.Attachment;
import com.jafarov.quiz.enums.OwnerType;
import com.jafarov.quiz.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
public class AttachmentService {

    private static final String UPLOAD_DIR = "uploads/";

    private final AttachmentRepository attachmentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

//    public void upload(Long ownerId,
//                       OwnerType ownerType,
//                       MultipartFile file
//    ) {
//        if (file.isEmpty()) return;
//
//        try {
//            Path uploadPath = Paths.get(UPLOAD_DIR);
//            if (!Files.exists(uploadPath)) {
//                Files.createDirectories(uploadPath);
//            }
//
//            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//            Path filePath = uploadPath.resolve(fileName);
//            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);
//            String fileUrl = "/uploads/" + fileName;
//
//            attachmentRepository.findByOwnerIdAndOwnerType(ownerId, ownerType)
//                    .ifPresent(existing -> {
//                        deleteFile(existing.getFileUrl());
//                        attachmentRepository.delete(existing);
//                    });
//
//            Attachment attachment = new Attachment();
//            attachment.setFileName(fileName);
//            attachment.setFileUrl(fileUrl);
//            attachment.setOwnerId(ownerId);
//            attachment.setOwnerType(OwnerType.valueOf(ownerType.name()));
//            attachmentRepository.save(attachment);
//
//        } catch (IOException e) {
//            throw new RuntimeException("File upload failed: " + e.getMessage());
//        }
//    }

    public Attachment uploadAndReturn(Long ownerId,
                                      OwnerType ownerType,
                                      MultipartFile file
    ) {
        if (file.isEmpty()) return null;

        try {
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes(), StandardOpenOption.CREATE);

            String fileUrl = "/uploads/" + fileName;

            attachmentRepository.findByOwnerIdAndOwnerType(ownerId, ownerType)
                    .ifPresent(existing -> {
                        deleteFile(existing.getFileUrl());
                        attachmentRepository.delete(existing);
                    });

            Attachment attachment = new Attachment();
            attachment.setFileName(fileName);
            attachment.setFileUrl(fileUrl);
            attachment.setOwnerId(ownerId);
            attachment.setOwnerType(ownerType);
            return attachmentRepository.save(attachment);

        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    public void deleteByOwner(Long ownerId,
                              OwnerType ownerType
    ) {
        attachmentRepository.findByOwnerIdAndOwnerType(ownerId, ownerType)
                .ifPresent(attachment -> {
                    deleteFile(attachment.getFileUrl());
                    attachmentRepository.delete(attachment);
                });
    }

    private void deleteFile(String fileUrl) {
        File file = new File("src/main/resources/static" + fileUrl);
        if (file.exists()) {
            file.delete();
        }
    }

}
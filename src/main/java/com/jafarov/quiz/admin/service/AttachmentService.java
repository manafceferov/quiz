package com.jafarov.quiz.admin.service;

import com.jafarov.quiz.admin.entity.Attachment;
import com.jafarov.quiz.admin.entity.User;
import com.jafarov.quiz.admin.repository.AttachmentRepository;
import com.jafarov.quiz.admin.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class AttachmentService {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, UserRepository userRepository) {
        this.attachmentRepository = attachmentRepository;
        this.userRepository = userRepository;
    }

    public void upload(Long userId, MultipartFile file)  {

        try{

        if (file.isEmpty()) {
            throw new IllegalArgumentException("Fayl boşdur");
        }

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        Files.write(filePath, file.getBytes());

        String fileUrl = "/uploads/" + fileName;

        // Attachment yaratmaq
        Attachment attachment = new Attachment(
                0L,
                fileName,
                fileUrl,
                LocalDateTime.now(),
                userId  // sourceId - istifadəçi ID-si
        );

        Attachment savedAttachment = attachmentRepository.save(attachment);

        // İstifadəçini tapmaq
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("İstifadəçi tapılmadı"));

        // İstifadəçini yeniləmək (əgər lazımdırsa)
        User updatedUser = new User(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getFatherName(),
                user.getEmail(),
                user.getPassword()
        );



        userRepository.save(updatedUser);
        }catch(Exception ex){
            System.out.println("Fayl əlavə edin");
        }
    }

}

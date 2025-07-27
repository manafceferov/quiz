package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.topic.TopicWithQuestionCountProjection;
import com.jafarov.quiz.dto.topic.TopicWithQuestionCountProjectionDto;
import com.jafarov.quiz.enums.OwnerType;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.repository.ParticipantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentService attachmentService;
    private final TopicService topicService;

    public ParticipantService(ParticipantRepository participantRepository,
                              PasswordEncoder passwordEncoder,
                              AttachmentService attachmentService,
                              TopicService topicService
    ) {
        this.participantRepository = participantRepository;
        this.passwordEncoder = passwordEncoder;
        this.attachmentService = attachmentService;
        this.topicService = topicService;
    }

    public Participant register(String firstName,
                                String lastName,
                                String email,
                                String password,
                                String confirmPassword,
                                MultipartFile file
    ) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Şifrə və təsdiq uyğun gəlmir");
        }

        if (participantRepository.findByEmail(email) != null) {
            throw new IllegalArgumentException("Bu email artıq qeydiyyatdan keçib");
        }

        Participant participant = new Participant();
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.setEmail(email);
        participant.setPassword(passwordEncoder.encode(password));
        participant.setStatus(true);
        participant = participantRepository.save(participant);

        if (file != null && !file.isEmpty()) {
            attachmentService.upload(participant.getId(), OwnerType.PARTICIPANT, file);
        }
        return participant;
    }

    public Page<TopicWithQuestionCountProjection> getAllTopics(Pageable pageable) {
        return topicService.getAllTopics(pageable);
    }

}

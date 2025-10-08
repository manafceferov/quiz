package com.jafarov.quiz.service;

import com.jafarov.quiz.dto.participant.ParticipantListDto;
import com.jafarov.quiz.dto.profile.ProfileProjectionEdit;
import com.jafarov.quiz.dto.profile.ProfileProjectionEditDto;
import com.jafarov.quiz.entity.Admin;
import com.jafarov.quiz.entity.Attachment;
import com.jafarov.quiz.entity.Participant;
import com.jafarov.quiz.enums.OwnerType;
import com.jafarov.quiz.mapper.ParticipantMapper;
import com.jafarov.quiz.repository.ParticipantRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final PasswordEncoder passwordEncoder;
    private final AttachmentService attachmentService;
    private final TopicService topicService;
    private final ParticipantMapper participantMapper;

    public ParticipantService(ParticipantRepository participantRepository,
                              PasswordEncoder passwordEncoder,
                              AttachmentService attachmentService,
                              TopicService topicService,
                              ParticipantMapper participantMapper
    ) {
        this.participantRepository = participantRepository;
        this.passwordEncoder = passwordEncoder;
        this.attachmentService = attachmentService;
        this.topicService = topicService;
        this.participantMapper = participantMapper;
    }

    public Participant register(String firstName,
                                String lastName,
                                String email,
                                String password,
                                String confirmPassword
    ) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Şifrə və təsdiq uyğun gəlmir");
        }

        if (participantRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Bu email artıq qeydiyyatdan keçib");
        }

        Participant participant = new Participant();
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.setEmail(email);
        participant.setPassword(passwordEncoder.encode(password));
        participant.setStatus(true);
        participant = participantRepository.save(participant);

        return participant;
    }

    public Participant findByEmail(String username) {
        return participantRepository.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found with email: " + username));
    }

    @Transactional
    public void updateProfile(Long id,
                              ProfileProjectionEditDto dto,
                              MultipartFile file
    ) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found with ID: " + id));

        participant.setFirstName(dto.getFirstName());
        participant.setLastName(dto.getLastName());
        participant.setFatherName(dto.getFatherName());
        participant.setEmail(dto.getEmail());
        participant.setPhoneNumber(dto.getPhoneNumber());
        participant.setBirthDate(dto.getBirthDate());
        participant.setGender(dto.getGender());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            if (!dto.getPassword().equals(dto.getConfirmPassword())) {
                throw new IllegalArgumentException("Şifrə və təsdiq uyğun gəlmir");
            }
            participant.setPassword(passwordEncoder.encode(dto.getPassword()));
        }


        if (file != null && !file.isEmpty()) {
            attachmentService.deleteByOwner(participant.getId(), OwnerType.PARTICIPANT);
            Attachment newAttachment = attachmentService.uploadAndReturn(participant.getId(), OwnerType.PARTICIPANT, file);
            participant.setAttachment(newAttachment);
        }

        participantRepository.save(participant);
    }

    public ProfileProjectionEditDto getProfile(Long participantId) {
        ProfileProjectionEdit projection = participantRepository.findProfileProjectionById(participantId)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found with ID: " + participantId));

        ProfileProjectionEditDto dto = new ProfileProjectionEditDto(
                projection.getId(),
                projection.getFirstName(),
                projection.getLastName(),
                projection.getFatherName(),
                projection.getEmail(),
                projection.getPassword(),
                projection.getConfirmPassword(),
                projection.getPhoneNumber(),
                projection.getBirthDate(),
                projection.getGender(),
                projection.getAttachment(),
                projection.getAttachmentUrl()
        );

        if (projection.getAttachment() != null) {
            dto.setAttachmentUrl("/attachments/" + projection.getAttachment());
        }

        return dto;
    }

    public Participant findById(Long id) {
        return participantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Participant not found with ID: " + id));
    }

    @Transactional
    public void changeStatus(Long id, Boolean status) {
        participantRepository.changeStatus(id, status);
    }

    public Page<ParticipantListDto> getAll(Pageable pageable) {
        return participantRepository.findAll(pageable)
                .map(participantMapper::toListDto);
    }

    public Page<Participant> searchUsers(String name, Pageable pageable) {
        if (name != null && !name.trim().isEmpty()) {
            return participantRepository.searchByFullName(name.trim(), pageable);
        }
        return participantRepository.findAll(pageable);
    }
}

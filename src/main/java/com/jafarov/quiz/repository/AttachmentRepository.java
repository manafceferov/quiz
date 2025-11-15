package com.jafarov.quiz.repository;

import com.jafarov.quiz.entity.Attachment;
import com.jafarov.quiz.enums.OwnerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByOwnerIdAndOwnerType(Long ownerId, OwnerType ownerType);
}

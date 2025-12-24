package com.quiz.repository;

import com.quiz.entity.Attachment;
import com.quiz.enums.OwnerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    Optional<Attachment> findByOwnerIdAndOwnerType(Long ownerId, OwnerType ownerType);
}

package com.quiz.repository;

import com.quiz.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long>, JpaSpecificationExecutor<Admin> {

    @Transactional(readOnly = true)
    Optional<Admin> findByEmail(String email);

    @EntityGraph(attributePaths = {"attachment"})
    Page<Admin> findAll(Pageable pageable);

    @Query("SELECT a FROM Admin a LEFT JOIN FETCH a.attachment WHERE a.id = :id")
    Admin getUserByIdWithAttachment(@Param("id") Long id);

    @Query("SELECT a FROM Admin a WHERE " + "LOWER(CONCAT(a.firstName, ' ', a.lastName, ' ', a.fatherName)) " +
           "LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Admin> searchByFullName(@Param("name") String name, Pageable pageable);

    @Modifying
    @Query("UPDATE Admin a SET a.status = :status WHERE a.id = :id")
    void changeStatus(@Param("id") Long id,@Param("status") Boolean status);

    Boolean existsByEmail(String email);
}

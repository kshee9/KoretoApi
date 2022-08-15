package com.example.koreto.repository;

import com.example.koreto.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {


//    Optional<Attachment> findByIDByCreatedDateDesc(Integer articleid);
}

package com.diytracker.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diytracker.app.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

}

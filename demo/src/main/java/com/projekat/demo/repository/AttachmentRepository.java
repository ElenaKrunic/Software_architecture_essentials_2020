package com.projekat.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Attachment;

public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

}

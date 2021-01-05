package com.projekat.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Attachment;
import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

	public List<Attachment> findByMessage(MMessage message);
	
	public Attachment findById(Integer id);
}

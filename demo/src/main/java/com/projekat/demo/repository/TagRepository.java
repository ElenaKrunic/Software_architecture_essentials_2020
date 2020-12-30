package com.projekat.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projekat.demo.entity.MMessage;
import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

	public Tag findByIdAndUser(Integer id, User user);

	public List<Tag> findByMessages(MMessage message);
}

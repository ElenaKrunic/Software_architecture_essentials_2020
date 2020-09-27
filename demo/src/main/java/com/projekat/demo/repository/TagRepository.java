package com.projekat.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Tag;
import com.projekat.demo.entity.User;


public interface TagRepository extends JpaRepository<Tag, Integer> {

	public Tag findByIdAndUser(Integer id, User user);
}

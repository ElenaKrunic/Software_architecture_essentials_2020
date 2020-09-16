package com.projekat.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

}

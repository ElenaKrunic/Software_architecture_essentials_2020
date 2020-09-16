package com.projekat.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Photo;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

}

package com.projekat.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Folder;

public interface FolderRepository extends JpaRepository<Folder, Integer> {

}

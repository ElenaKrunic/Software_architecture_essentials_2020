package com.projekat.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projekat.demo.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

}

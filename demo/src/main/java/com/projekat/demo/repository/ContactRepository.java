package com.projekat.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}

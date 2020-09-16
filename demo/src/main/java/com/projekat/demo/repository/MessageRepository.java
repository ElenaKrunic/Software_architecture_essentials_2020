package com.projekat.demo.repository;

//import javax.mail.Message;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.MMessage;


public interface MessageRepository extends JpaRepository<MMessage, Integer> {

}

package com.projekat.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projekat.demo.entity.Rule;

public interface RuleRepository extends JpaRepository<Rule, Integer> {

}

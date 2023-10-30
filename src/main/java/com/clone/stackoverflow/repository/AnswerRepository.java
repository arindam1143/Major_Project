package com.clone.stackoverflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.stackoverflow.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long>{

}

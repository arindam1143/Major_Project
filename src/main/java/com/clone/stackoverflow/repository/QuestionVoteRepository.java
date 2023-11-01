package com.clone.stackoverflow.repository;

import com.clone.stackoverflow.model.AnswerVote;
import com.clone.stackoverflow.model.QuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long>{

}

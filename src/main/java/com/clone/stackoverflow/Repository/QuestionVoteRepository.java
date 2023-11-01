package com.clone.stackoverflow.Repository;

import com.clone.stackoverflow.model.AnswerVote;
import com.clone.stackoverflow.model.QuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long>{

}
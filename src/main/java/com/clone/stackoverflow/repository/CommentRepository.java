package com.clone.stackoverflow.repository;

import com.clone.stackoverflow.model.Answer;
import com.clone.stackoverflow.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>{

}

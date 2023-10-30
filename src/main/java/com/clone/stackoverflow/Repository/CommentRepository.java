package com.clone.stackoverflow.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clone.stackoverflow.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

}

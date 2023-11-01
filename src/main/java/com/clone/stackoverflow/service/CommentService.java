package com.clone.stackoverflow.service;

import com.clone.stackoverflow.Repository.AnswerRepository;
import com.clone.stackoverflow.Repository.CommentRepository;
import com.clone.stackoverflow.Repository.QuestionRepository;
import com.clone.stackoverflow.Repository.UserRepository;
import com.clone.stackoverflow.model.Comment;
import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    UserRepository userRepository;
    public void createQuestionComment(Long questionId, String commentContent) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User author = userRepository.getUserByUserName(username);
		//model.addAttribute("author",author);
        Comment comment = new Comment();
        comment.setContent(commentContent);
        comment.setQuestion(questionRepository.findById(questionId).get());
        comment.setUser(author);
        commentRepository.save(comment);
    }

    public void createAnswerComment(Long answerId, String commentContent) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User author = userRepository.getUserByUserName(username);
        Comment comment = new Comment();
        comment.setContent(commentContent);
        comment.setAnswer(answerRepository.findById(answerId).get());
        comment.setUser(author);
        commentRepository.save(comment);
    }

    public Long updateComment(String commentContent, Long id) {
        Comment comment = commentRepository.findById(id).get();
        comment.setContent(commentContent);
        Comment saveComment = commentRepository.save(comment);
        if (saveComment.getAnswer()!=null)
            return saveComment.getAnswer().getQuestion().getId();
        return saveComment.getQuestion().getId();
    }
}
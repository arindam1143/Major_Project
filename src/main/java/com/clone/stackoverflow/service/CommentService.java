package com.clone.stackoverflow.service;

import com.clone.stackoverflow.model.Comment;
import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.repository.AnswerRepository;
import com.clone.stackoverflow.repository.CommentRepository;
import com.clone.stackoverflow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void createQuestionComment(Long questionId, String commentContent) {
        Comment comment = new Comment();
        comment.setContent(commentContent);
        comment.setQuestion(questionRepository.findById(questionId).get());
        commentRepository.save(comment);
    }

    public void createAnswerComment(Long answerId, String commentContent) {
        Comment comment = new Comment();
        comment.setContent(commentContent);
        comment.setAnswer(answerRepository.findById(answerId).get());
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

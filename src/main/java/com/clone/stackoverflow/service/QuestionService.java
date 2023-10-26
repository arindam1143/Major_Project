package com.clone.stackoverflow.service;

import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TagService tagService;


    public Question createQuestion(Question question, String tagString) {
        if (question.getId() == null) {
            if (tagString != null && !tagString.isEmpty())
                question.setTags(tagService.getPostTags(tagString));
//            question.setUser(userRepository.findByNameAndRole(question.getAuthor(), "USER"));
            return questionRepository.save(question);
        } else {
            Optional<Question> optional = questionRepository.findById(question.getId());
            Question updateQuestion = optional.get();
            updateQuestion.setContent(question.getContent());
            updateQuestion.setTitle(question.getTitle());
            if (tagString != null && !tagString.isEmpty())
                updateQuestion.setTags(tagService.getPostTags(tagString));
            return questionRepository.save(updateQuestion);
        }
    }
}

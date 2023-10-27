package com.clone.stackoverflow.service;

import com.clone.stackoverflow.Repository.questionRepository;
import com.clone.stackoverflow.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private questionRepository questionRepository;

    public Question addTag(Question question)
    {
        return questionRepository.save(question);
    }
    public List<Question> getQuestionsByTagName(String tagName) {
        return questionRepository.findByTagsName(tagName);
    }

}

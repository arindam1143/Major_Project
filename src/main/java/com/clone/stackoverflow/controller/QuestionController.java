package com.clone.stackoverflow.controller;

import com.clone.stackoverflow.repository.QuestionRepository;
import com.clone.stackoverflow.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
}

package com.clone.stackoverflow.controller;

import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.repository.QuestionRepository;
import com.clone.stackoverflow.repository.TagRepository;
import com.clone.stackoverflow.service.QuestionService;
import org.springframework.stereotype.Controller;

import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.model.Tag;
import com.clone.stackoverflow.service.QuestionService;
import com.clone.stackoverflow.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;


@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/ask")
    public String displayQuestionForm(Model model) {
        model.addAttribute("tagList",tagRepository.findAllTagNames());
        return "AskQuestion";
    }

    @PostMapping("/create")
    public String createQuestion(@ModelAttribute Question question, @RequestParam String tagString, Model model) {
        Question createdQuestion = questionService.createQuestion(question, tagString);
        model.addAttribute("question", createdQuestion);
        return "redirect:/question?id="+createdQuestion.getId();
    }


    @GetMapping("/delete/{questionId}")
    public String deleteQuestion(@PathVariable Long questionId){
        questionRepository.deleteById(questionId);
        return "Question";
    }

}

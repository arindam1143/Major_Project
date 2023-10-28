package com.clone.stackoverflow.controller;

import org.springframework.stereotype.Controller;

import com.clone.stackoverflow.Repository.QuestionRepository;
import com.clone.stackoverflow.Repository.TagRepository;
import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.model.Tag;
import com.clone.stackoverflow.service.QuestionService;
import com.clone.stackoverflow.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
	    
	    @Autowired
	    private TagService tagservice;

	    @GetMapping("/ask")
	    public String displayQuestionForm(Model model) {
	        model.addAttribute("tagList",tagRepository.findAllTagNames());
	        return "AskQuestion";
	    }

	    @PostMapping("/create")
	    public String createQuestion(@ModelAttribute Question question, @RequestParam("tagString") String tagString, Model model) {
//	    	Set<Tag> tagsValue = Arrays.stream(tagString.split(",")).map(tagName -> tagService.saveTag(tagName.trim()))
//	                .collect(Collectors.toSet());
//	        question.setTags(tagsValue);
	    	Question savedQuestion =questionService.createQuestion(question, tagString);
	        model.addAttribute("questions", savedQuestion);
	         
	        return "redirect:/question?id="+savedQuestion.getId();
	    }

	    @GetMapping("/{questionId}")
	    public String readQuestion(@PathVariable Long questionId, Model model) {
	        questionRepository.updateViewCount(questionId);
	        Optional<Question> optional = questionRepository.findById(questionId);
	        optional.ifPresent(question -> model.addAttribute("question", question));
	        return "Question";
	    }

	    @GetMapping("/delete")
	    public String deleteQuestion(@RequestParam("id") Long id){
	        questionRepository.deleteById(id);
	        return "redirect:/question?id="+id;
	    }
}

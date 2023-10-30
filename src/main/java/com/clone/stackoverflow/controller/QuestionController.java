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
import java.util.List;
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

	    @PostMapping("/deletequestion")
	    public String deleteQuestion(@RequestParam("id") Long id,Model model){
	        questionRepository.deleteById(id);
	        List<Question> question=questionRepository.findAll();
			model.addAttribute("questions",question);
			return "redirect:/home";
	    }
	    
	    @PostMapping("/updatequestion")
	    public String UpdateQuestion(@RequestParam("id") Long id,Model model) {
	    	Question question=questionRepository.findById(id).get();
	    	model.addAttribute("question", question);
	    	return "UpdateQuestion"; 
	    }
	    
	    @PostMapping("/saveupdatequestion")
	    public String SaveUpdateQuestion(
	    		@RequestParam("question") String question,
	    		@RequestParam("id") Long id,Model model) {
	    	Question preQuestion =questionRepository.findById(id).get();
	    	preQuestion.setContent(question);
	    	questionRepository.save(preQuestion);
	    	List<Question> questions=questionRepository.findAll();
			model.addAttribute("questions",questions);
			return "redirect:/home";
	    
	    }
	    @PostMapping("/update")
		public String updateQuestionForm(@RequestParam Long questionId,Model model){
			Question question = questionRepository.findById(questionId).get();
			StringBuilder tagString=new StringBuilder("");
			question.getTags().forEach(tag->{tagString.append(tag.getName()+",");});
			model.addAttribute("updateQuestion", question);
			model.addAttribute("tagString", tagString);
	        model.addAttribute("tagList",tagRepository.findAllTagNames());
			return "UpdateQuestion";
		}

	    @PostMapping("/delete")
	    public String deleteQuestion(@RequestParam Long questionId){
	        questionRepository.deleteById(questionId);
	        return "redirect:/home";
	    }
}

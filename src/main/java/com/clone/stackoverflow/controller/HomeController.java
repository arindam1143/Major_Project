package com.clone.stackoverflow.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clone.stackoverflow.Repository.AnswerRepository;
import com.clone.stackoverflow.Repository.QuestionRepository;
import com.clone.stackoverflow.Repository.UserRepository;
import com.clone.stackoverflow.model.Answer;
import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.model.User;
import com.clone.stackoverflow.service.HomeService;

import org.springframework.ui.Model;


@Controller
public class HomeController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	HomeService homeService;
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	AnswerRepository answerRepository;
	
	@GetMapping("/")
	public String open() {
		return "Home";
	}
	@GetMapping("/user")
	public String UserProfile() {
		return "UserProfile";
	}
	
	@GetMapping("/home")
	public String Home(Model model) {
		
		List<Question> question=questionRepository.findAll();
		model.addAttribute("questions",question);
		return "HomePage";
	}
	@GetMapping("/question")
	public String QuestionPage(@RequestParam("id") Long id,Model model) {
		questionRepository.updateViewCount(id);
		Question question=questionRepository.findById(id).get();
		model.addAttribute("questions",question);
		List<Answer> anslist=question.getAnswers();
		model.addAttribute("anslist",anslist);
		
		return "ShowQuestion";
	}
	@GetMapping("/signup")
	public String SignUp() {
		return "SignUp";
	}
	
	@PostMapping("/register")
	public String SaveUserDate(
			@RequestParam("name") String name, 
			@RequestParam("email") String email,
			@RequestParam("password1") String password1,
			@RequestParam("password2") String password2) {
		User userObject = new User();
		userObject.setUsername(name);
		userObject.setEmail(email);
		System.out.print(password1);
		System.out.print(password2);
		if(password1.equals(password2)) {
			userObject.setPassword(password2);
			userRepository.save(userObject);
			return "redirect:/home";
			
		}else {
			return "redirect:/signup";
		}
		
		
	}
	@GetMapping("/search")
	public String Search(@RequestParam("search") String searchText,Model model) {
		Set<Question> setofQuestion= homeService.searchQuestion(searchText);
		model.addAttribute("questions",setofQuestion);
		//System.out.println("this is home controller");
		return "HomePage";
	}
	@PostMapping("/saveanswer")
	public String SaveAnswer(
			@RequestParam("answer") String answer,
			@RequestParam("id") Long id,Model model) {
		Question question=questionRepository.findById(id).get();
		Answer ansobj=new Answer();
		ansobj.setContent(answer);
		ansobj.prePersist();
		ansobj.setQuestion(question);
		answerRepository.save(ansobj);
		model.addAttribute("answer",ansobj);
		
		return "redirect:/question?id="+id;
	}
	
	

}

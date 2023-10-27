package com.clone.stackoverflow.controller;

import java.util.List;
import java.util.Set;

import com.clone.stackoverflow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clone.stackoverflow.repository.UserRepository;
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
	
	@GetMapping("/home")
	public String Home(Model model) {
		
		List<Question> question=questionRepository.findAll();
		//System.out.print(question.size()+"size of list");
		model.addAttribute("questions",question);
		return "HomePage";
	}
	@GetMapping("/question")
	public String QuestionPage(@RequestParam(value = "id") Long id,Model model) {
		questionRepository.updateViewCount(id);
		Question question=questionRepository.findById(id).get();
		model.addAttribute("questions",question);
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
		System.out.println("this is home controller");
		return "redirect:/home";
	}

}

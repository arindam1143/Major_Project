package com.clone.stackoverflow.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeController {
	@GetMapping("/home")
	public String Home() {
		
		return "HomePage";
	}
	
	@GetMapping("/signup")
	public String SignUp() {
		
		return "SignUp";
	}

}

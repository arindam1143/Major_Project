package com.clone.stackoverflow.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.clone.stackoverflow.Repository.QuestionRepository;
import com.clone.stackoverflow.Repository.TagRepository;
import com.clone.stackoverflow.Repository.UserRepository;
import com.clone.stackoverflow.model.Question;
@Component
public class HomeService {
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	TagRepository tagRepository;
	
	public Set<Question> searchQuestion(String searchText) {
		String[] searchEle = searchText.split(" ");
		System.out.println("this is home service");
		Set<Question> questions = new HashSet<>();
		for (String ele : searchEle) {
			List<Question> namePosts=userRepository.searchByName(ele);
			List<Question> tagPosts = tagRepository.searchByTag(ele);
			List<Question> titlePosts = questionRepository.searchByTitle(ele);
			List<Question> contentPosts = questionRepository.searchByContent(ele);
			questions.addAll(namePosts);
			questions.addAll(contentPosts);
			questions.addAll(tagPosts);
			questions.addAll(titlePosts);
		}
		
		
		return questions;

	}

}

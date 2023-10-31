package com.clone.stackoverflow.controller;

import java.util.List;

import com.clone.stackoverflow.repository.AnswerRepository;
import com.clone.stackoverflow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.clone.stackoverflow.model.Answer;
import com.clone.stackoverflow.model.Question;
import org.springframework.ui.Model;

@Controller
public class AnswerController {
	@Autowired
	AnswerRepository answerRepository;
	@Autowired
	private QuestionRepository questionRepository;

	@PostMapping("/deleteans")
	public String DeleteAnswer(
			@RequestParam("qid") Long qid,
			@RequestParam("id") Long id,Model model) {
		Question question=questionRepository.findById(qid).get();
		model.addAttribute("questions",question);
		List<Answer> anslist=question.getAnswers();
		model.addAttribute("anslist",anslist);
		answerRepository.deleteById(id);

		return "ShowQuestion";
	}
	@PostMapping("/updateans")
	public String UpdateAnswer(
			@RequestParam("qid") Long qid,
			@RequestParam("id") Long id,Model model) {
		Answer answer=answerRepository.findById(id).get();
		Question question=questionRepository.findById(qid).get();	
		String val=answer.getContent();
		model.addAttribute("answer",answer);
		
		return "UpdateContent";
	}
	
	@PostMapping("/saveupdate")
	public String SaveUpdate(
			@RequestParam("id") Long id,
			@RequestParam("answer") String updateanswer) {
		Answer answer=answerRepository.findById(id).get();
//		System.out.println(updateanswer);
		answer.setContent(updateanswer);
		answerRepository.save(answer);
		return "redirect:/question?id="+answer.getQuestion().getId();
	}

	@PostMapping("/upvoteAns/{answerId}/{questionId}")
	public String upvoteAnswer(@PathVariable Long answerId,@PathVariable Long questionId) {
		Answer answer=answerRepository.findById(answerId).get();
			answer.setUpVote(answer.getUpVote() + 1);
			answerRepository.save(answer);
		return "redirect:/question?id="+questionId;
	}
	@PostMapping("/downvoteAns/{answerId}/{questionId}")
	public String downVoteAnswer(@PathVariable Long answerId,@PathVariable Long questionId) {
		Answer answer=answerRepository.findById(answerId).get();
			answer.setDownVote(answer.getDownVote() + 1);
			answerRepository.save(answer);
		return "redirect:/question?id="+questionId;
	}

	@GetMapping("/delete/{questionId}")
	public String deleteQuestion(@PathVariable Long questionId){
		questionRepository.deleteById(questionId);
		return "Questions";
	}

}

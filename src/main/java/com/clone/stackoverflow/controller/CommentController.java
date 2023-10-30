package com.clone.stackoverflow.controller;


import com.clone.stackoverflow.Repository.CommentRepository;
import com.clone.stackoverflow.model.Answer;
import com.clone.stackoverflow.model.Comment;
import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private CommentRepository commentRepository;

	@PostMapping("/question")
	public String createQuestionComment(@RequestParam Long questionId, @RequestParam String commentContent){
		commentService.createQuestionComment(questionId,commentContent);
		return "redirect:/question?id="+questionId;
	}

	@PostMapping("/answer")
	public String createAnswerComment(@RequestParam Long questionId,@RequestParam Long answerId,@RequestParam String commentContent){
		commentService.createAnswerComment(answerId,commentContent);
		return "redirect:/question?id="+questionId;
	}

	@GetMapping("/delete/{commentId}/{questionId}")
	public String deleteComment(@PathVariable Long commentId,@PathVariable Long questionId){
		commentRepository.deleteById(commentId);
		return "redirect:/question?id="+questionId;
	}

	@GetMapping("/update/{commentId}")
	public String updateCommentForm(@PathVariable Long commentId,Model model){
		model.addAttribute("comment",commentRepository.findById(commentId).get());
		return "UpdateComment";
	}

	@PostMapping("/update")
	public String updateComment(@RequestParam String commentContent,@RequestParam Long id,Model model){
		Long questionId = commentService.updateComment(commentContent, id);
		return "redirect:/question?id="+questionId;
	}
}
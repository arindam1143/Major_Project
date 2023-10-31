package com.clone.stackoverflow.controller;

import com.clone.stackoverflow.repository.QuestionRepository;
import com.clone.stackoverflow.repository.TagRepository;
import jakarta.websocket.server.PathParam;
import org.springframework.stereotype.Controller;

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
        model.addAttribute("tagList", tagRepository.findAllTagNames());
        return "AskQuestion";
    }

    @PostMapping("/create")
    public String createQuestion(@ModelAttribute Question question, @RequestParam("tagString") String tagString, Model model) {
        Question savedQuestion = questionService.createQuestion(question, tagString);
        model.addAttribute("questions", savedQuestion);

        return "redirect:/question?id=" + savedQuestion.getId();
    }

    @GetMapping("/{questionId}")
    public String readQuestion(@PathVariable Long questionId, Model model) {
        questionRepository.updateViewCount(questionId);
        Optional<Question> optional = questionRepository.findById(questionId);
        optional.ifPresent(question -> model.addAttribute("question", question));
        return "Question";
    }

    @PostMapping("/update")
    public String updateQuestionForm(@RequestParam Long questionId, Model model) {
        Question question = questionRepository.findById(questionId).get();
        StringBuilder tagString = new StringBuilder("");
        question.getTags().forEach(tag -> {
            tagString.append(tag.getName() + ",");
        });
        model.addAttribute("updateQuestion", question);
        model.addAttribute("tagString", tagString);
        model.addAttribute("tagList", tagRepository.findAllTagNames());
        return "UpdateQuestion";
    }


    @PostMapping("/setAnswer")
    public String setAcceptedQuestion(@RequestParam Long answerId, @RequestParam Long questionId) {
        questionRepository.setAcceptedAnswer(answerId, questionId);
        return "redirect:/question?id=" + questionId;
    }

    @PostMapping("/removeAnswer")
    public String removeAcceptedAnswer(@RequestParam Long questionId) {
        questionRepository.removeAcceptedAnswer(questionId);
        return "redirect:/question?id=" + questionId;
    }

    @PostMapping("/upvote/{id}")
    public String upvoteQuestion(@PathVariable Long id) {
        Question question = questionRepository.findById(id).get();
        if (question.getUpVote() >= 0) {
            question.setUpVote(question.getUpVote() + 1);
            questionService.addTag(question);
        }
        return "redirect:/question?id=" + id;
    }

    @PostMapping("/downvote/{id}")
    public String downVoteQuestion(@PathVariable Long id, Model model) {
        Question question = questionRepository.findById(id).get();
        if (question.getDownVote() >= 0) {
            question.setDownVote(question.getDownVote() + 1);
            questionService.addTag(question);
        }
        return "redirect:/question?id=" + id;
    }

    @GetMapping("/delete/{questionId}")
    public String deleteQuestion(@PathVariable Long questionId) {
        questionRepository.deleteById(questionId);
        return "Questions";
    }

}

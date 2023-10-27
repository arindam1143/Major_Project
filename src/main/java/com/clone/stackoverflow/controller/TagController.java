package com.clone.stackoverflow.controller;

import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.model.Tag;
import com.clone.stackoverflow.service.QuestionService;
import com.clone.stackoverflow.service.TagService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TagController {
@Autowired
private TagService tagService;


@Autowired
private QuestionService questionService;


    @GetMapping("/tags")
    public String postTags(Model model) {
        Tag tag = new Tag();
        Question question = new Question();
        model.addAttribute("enterTags", tag);
        model.addAttribute("questionTags" , question);
        return "EnterTags";
    }

    @PostMapping("/submit-tags")
    public String saveTags(@RequestParam("name") String tagsName, Question question){
        Set<Tag> tagsValue = Arrays.stream(tagsName.split(",")).map(tagName -> tagService.saveTag(tagName.trim()))
                .collect(Collectors.toSet());
        question.setTags(tagsValue);
        questionService.addTag(question);
        return "redirect:/";
    }

    @GetMapping("tags/{name}")
    public String getQuestions(@PathVariable String name,Model model){

        List<Question> questions = questionService.getQuestionsByTagName(name);
        model.addAttribute("questions" , questions);
        return "Questions";
    }
    @GetMapping
    public String getTags(
            @RequestParam(value = "start", required = false) Integer pageNo,
            @RequestParam(required = false, value = "sort") String order,
            @RequestParam(required = false, value = "query") String query,
            Model model){
        int pageSize = 18;
        if (pageNo == null) {
            pageNo = 1;
        }
        Page<Tag> page = tagService.findPage(pageNo, pageSize, order,query);
        List<Tag> tagList = page.getContent();
        model.addAttribute("query",query);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("allTags", tagList);
        model.addAttribute("sortDir", order);
        return "TagPage";
    }





}

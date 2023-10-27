package com.clone.stackoverflow.controller;

import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.model.Tag;
import com.clone.stackoverflow.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class TagController {
@Autowired
private TagService tagService;


    @GetMapping("/tags")
    public String postTags(Model model) {
        Tag tag = new Tag();
        model.addAttribute("enterTags", tag);
        return "EnterTags";
    }

    @PostMapping("/submit-tags")
    public String saveTags(@RequestParam("name") String tagsName){
        Set<Tag> tagsValue = Arrays.stream(tagsName.split(",")).map(tagName -> tagService.saveTag(tagName.trim()))
                .collect(Collectors.toSet());
        return "redirect:/";
    }


    @GetMapping
    public String getTags(
            @RequestParam(value = "start", required = false) Integer pageNo,
            @RequestParam(required = false, value = "sort") String order,Model model){
        int pageSize = 10;
        if (pageNo == null) {
            pageNo = 1;
        }
        Page<Tag> page = tagService.findPage(pageNo, pageSize, order);
        List<Tag> tagList = page.getContent();
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("allTags", tagList);
        model.addAttribute("sortDir", order);
        return "TagPage";
    }

}

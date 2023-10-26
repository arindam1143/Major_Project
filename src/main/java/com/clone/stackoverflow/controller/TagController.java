package com.clone.stackoverflow.controller;

import com.clone.stackoverflow.model.Tag;
import com.clone.stackoverflow.repository.TagRepository;
import com.clone.stackoverflow.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagRepository tagRepository;

    @PostMapping("/tag/update")
    public String updateTag(@ModelAttribute Tag tag){
        tagService.updateTag(tag);
        return "";
    }

    @GetMapping("/tag/create")
    public String createTag(@RequestParam String tagName){
        tagService.createTag(tagName);
        return "";
    }

}

package com.clone.stackoverflow.service;

import com.clone.stackoverflow.model.Tag;
import com.clone.stackoverflow.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public Tag createTag(String tagName){
        Tag tag = new Tag();
        tag.setName(tagName);
        return tagRepository.save(tag);
    }

    public void updateTag(Tag tag){
        Optional<Tag> optional = tagRepository.findById(tag.getId());
        if (optional.isPresent()){
            Tag updatedTag = optional.get();
            updatedTag.setName(tag.getName());
            tagRepository.save(updatedTag);
        }
    }

    public List<Tag> getPostTags(String tagString){
        String[] tagNameList = tagString.split(",");
        ArrayList<Tag> tags = new ArrayList<>();
        for(String tagName:tagNameList){
            Tag tag = tagRepository.findByName(tagName);
            if(tag==null){
                tag=createTag(tagName);
            }
            tags.add(tag);
        }
        return tags;
    }
}

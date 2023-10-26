package com.clone.stackoverflow.service;

import com.clone.stackoverflow.Repository.TagRepository;
import com.clone.stackoverflow.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;
    public Tag saveTag(String tags) {

        Tag retrivedTag = tagRepository.findByName(tags);
        if (retrivedTag != null) {
            return retrivedTag;
        }
        else {
            Tag newTag = new Tag();
            newTag.setName(tags);
            return tagRepository.save(newTag);
        }
    }

    public List<Tag> getPostByTag(){
        return  tagRepository.findAll();
    }
    public Page<Tag> findPage(int pageNo, int pageSize, String sort) {
        // Create a Pageable object with sorting
        Pageable pageable;
        if (sort != null) {
            switch (sort) {
                case "titleAsc":
                    pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.asc("name")));
                    break;
                default:
                    pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.asc("publishedDate")));
                    break;
            }
        } else {
            pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.asc("publishedDate")));
        }

        return tagRepository.findAll(pageable);
    }

}

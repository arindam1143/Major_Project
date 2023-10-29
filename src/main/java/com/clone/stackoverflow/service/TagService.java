package com.clone.stackoverflow.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.clone.stackoverflow.Repository.TagRepository;
import com.clone.stackoverflow.model.Tag;

@Service
public class TagService {
	@Autowired
	private TagRepository tagRepository;

	public Tag createTag(String tagName) {
		Tag tag = new Tag();
		tag.setName(tagName);
		tag.setQuestionCount(1L);
		return tagRepository.save(tag);
	}

	public void updateTag(Tag tag) {
		Optional<Tag> optional = tagRepository.findById(tag.getId());
		if (optional.isPresent()) {
			Tag updatedTag = optional.get();
			updatedTag.setName(tag.getName());
			tagRepository.save(updatedTag);
		}
	}

	public Set<Tag> getPostTags(String tagString) {
		String[] tagNameList = tagString.split(",");
		//ArrayList<Tag> tags = new ArrayList<>();
		Set<Tag> tags=new HashSet<>();
		for (String tagName : tagNameList) {
			Tag tag = tagRepository.findByName(tagName);
			if (tag == null) {
				tag = createTag(tagName);
			}else {
				tag.setQuestionCount(tag.getQuestionCount()+1);
				tag = tagRepository.save(tag);
			}
			tags.add(tag);
		}
		return tags;
	}
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
	    public Page<Tag> findPage(int pageNo, int pageSize, String sort ,String query) {
	        Pageable pageable;
	        if (sort != null) {
	            switch (sort) {
	                case "titleAsc":
	                    pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.asc("name")));
	                    break;
	                case "popular" :
						pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.desc("questionCount")));
	                    break;
	                default:
	                    pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.desc("publishedDate")));
	                    break;
	            }
	        } else {
	            pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Order.desc("publishedDate")));
	        }
	        if(query != null){
	            return tagRepository.searchTags(query, pageable);
	        }else

	            return tagRepository.findAll(pageable);
	    }

}

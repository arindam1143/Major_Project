package com.clone.stackoverflow.service;

import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.model.Tag;
import com.clone.stackoverflow.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TagService tagService;


    public Question createQuestion(Question question, String tagString) {
        if (question.getId() == null) {
            if (tagString != null && !tagString.isEmpty())
                question.setTags(tagService.getPostTags(tagString));
//            question.setUser(userRepository.findByNameAndRole(question.getAuthor(), "USER"));
            return questionRepository.save(question);
        } else {
            Optional<Question> optional = questionRepository.findById(question.getId());
            Question updateQuestion = optional.get();
            updateQuestion.setContent(question.getContent());
            updateQuestion.setTitle(question.getTitle());
            if (tagString != null && !tagString.isEmpty())
                updateQuestion.setTags(tagService.getPostTags(tagString));
            return questionRepository.save(updateQuestion);
        }
    }

    public Optional<Question> findQuestion(Long id){
        return questionRepository.findById(id);

    }


	public Question addTag(Question question)
    {
        return questionRepository.save(question);
    }
    public List<Question> getQuestionsByTagName(String tagName) {
        return questionRepository.findByTagsName(tagName);
    }

    public Page<Question> findPage(int pageNo, int pageSize, String sort) {
        Pageable pageable;
        if (sort!=null&&sort.equals("publishedDesc"))
            pageable=PageRequest.of(pageNo-1,pageSize,Sort.by(Sort.Order.desc("createdOn")));
        else
            pageable=PageRequest.of(pageNo-1,pageSize,Sort.by(Sort.Order.desc("view")));
//
        return questionRepository.findAll(pageable);
    }

    public  Long setUpVote(Question question){
        return question.getUpVote()+1;
    }

}

package com.clone.stackoverflow.Repository;

import com.clone.stackoverflow.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface questionRepository extends JpaRepository<Question, Long> {

        List<Question> findByTagsName(String tagName);

}

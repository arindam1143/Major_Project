package com.clone.stackoverflow.repository;

import com.clone.stackoverflow.model.Question;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {
    @Modifying
    @Transactional
    @Query("UPDATE Question q SET q.view=(q.view+1) WHERE id=:questionId  ")
    void updateViewCount(Long questionId);

    @Query("SELECT q FROM Question q WHERE q.title LIKE CONCAT('%', :searchText, '%')")
    public List<Question> searchByTitle(@Param("searchText") String searchText);
    @Query("SELECT q FROM Question q WHERE q.content LIKE CONCAT('%', :searchText, '%')")
    public List<Question> searchByContent(@Param("searchText") String searchText);

    List<Question> findByTagsName(String tagName);
}

package com.clone.stackoverflow.repository;

import java.util.List;

import com.clone.stackoverflow.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clone.stackoverflow.model.Question;

import jakarta.transaction.Transactional;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long>{
	@Query("SELECT q FROM Question q WHERE q.title LIKE CONCAT('%', :searchText, '%')")
	public List<Question> searchByTitle(@Param("searchText") String searchText);
	@Query("SELECT q FROM Question q WHERE q.content LIKE CONCAT('%', :searchText, '%')")
	public List<Question> searchByContent(@Param("searchText") String searchText);
	
	@Modifying
	@Transactional
    @Query("UPDATE Question q SET q.view=(q.view+1) WHERE id=:questionId  ")
    void updateViewCount(Long questionId);

	@Modifying
	@Transactional
    @Query("UPDATE Question q SET q.acceptedAnswerId=:answerId WHERE id=:questionId  ")
    void setAcceptedAnswer(Long answerId,Long questionId);

	@Modifying
	@Transactional
    @Query("UPDATE Question q SET q.acceptedAnswerId=-1 WHERE id=:questionId  ")
    void removeAcceptedAnswer(Long questionId);
	
	List<Question> findByTagsName(String tagName);

}

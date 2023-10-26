package com.clone.stackoverflow.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
	@Query("SELECT T.questions FROM Tag T WHERE LOWER(T.name) LIKE CONCAT('%', LOWER(:searchText), '%')")
    public List<Question> searchByTag(@Param("searchText") String searchText);


}

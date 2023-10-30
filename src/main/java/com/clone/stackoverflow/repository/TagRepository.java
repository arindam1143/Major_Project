package com.clone.stackoverflow.repository;

import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);

    @Query("SELECT t.name from Tag t")
    List<String> findAllTagNames();

    @Query("SELECT T.questions FROM Tag T WHERE LOWER(T.name) LIKE CONCAT('%', LOWER(:searchText), '%')")
    public List<Question> searchByTag(@Param("searchText") String searchText);

    @Query("SELECT t FROM Tag t WHERE (:query IS NULL OR lower( t.name) LIKE %:query%)")
    Page<Tag> searchTags(@Param("query") String searchQuery, Pageable page);

}

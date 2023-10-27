package com.clone.stackoverflow.Repository;

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

    @Query("SELECT t FROM Tag t WHERE lower( t.name) LIKE %:query%")
    Page<Tag> searchTags(@Param("query") String searchQuery, Pageable page);




}

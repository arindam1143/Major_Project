package com.clone.stackoverflow.repository;

import com.clone.stackoverflow.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {
    Tag findByName(String name);

    @Query("SELECT t.name from Tag t")
    List<String> findAllTagNames();
}

package com.clone.stackoverflow.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.model.Tag;
@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
	@Query("SELECT T.questions FROM Tag T WHERE LOWER(T.name) LIKE CONCAT('%', LOWER(:searchText), '%')")
    public List<Question> searchByTag(@Param("searchText") String searchText);

//	public Object findAllTagNames();
	public Tag findByName(String name);
	 @Query("SELECT t.name from Tag t")
	  List<String> findAllTagNames();
	 

	    @Query("SELECT t FROM Tag t WHERE lower( t.name) LIKE %:query%")
	    Page<Tag> searchTags(@Param("query") String searchQuery, Pageable page);


}

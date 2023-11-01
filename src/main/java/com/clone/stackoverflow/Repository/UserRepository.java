package com.clone.stackoverflow.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clone.stackoverflow.model.Question;
import com.clone.stackoverflow.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	@Query("SELECT u.questions FROM User u WHERE LOWER(u.username) LIKE CONCAT('%', LOWER(:searchText), '%')")
    public List<Question> searchByName(@Param("searchText") String searchText);
	
	@Query ("select u from User u where u.email= :username")
	User getUserByUserName(String username);

}

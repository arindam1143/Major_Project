package com.clone.stackoverflow.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "content",columnDefinition = "text")
	private String content;
	private Long view;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;

	private Long upVote;
	private Long downVote;

	@OneToMany(mappedBy = "answer")
	private List<Comment> comments;

	@Column(name = "created_on")
	private LocalDateTime createdOn;

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Question getQuestion() { 
		return question;
	}

	public Long getUpVote() {
		return upVote;
	}

	public void setUpVote(Long upVote) {
		this.upVote = upVote;
	}

	public Long getDownVote() {
		return downVote;
	}

	public void setDownVote(Long downVote) {
		this.downVote = downVote;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Long getView() {
		return view;
	}

	public void setView(Long view) {
		this.view = view;
	}

	@PrePersist
	public void prePersist() {
		createdOn = LocalDateTime.now();
		upVote=0L;
		downVote=0L;
		}

}
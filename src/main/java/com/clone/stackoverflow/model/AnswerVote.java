package com.clone.stackoverflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AnswerVote {
    @Id
    private Long id;
    private Long userId;
    private Long answerId;
    private Boolean voted;

    public Boolean getVoted() {
        return voted;
    }

    public void setVoted(Boolean voted) {
        this.voted = voted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long answerId) {
        this.answerId = answerId;
    }
}

package com.internhub.backend.models;

import javax.persistence.*;

@Entity
@Table(name = "suggestions")
public class Suggestion {
    @GeneratedValue
    @Id
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

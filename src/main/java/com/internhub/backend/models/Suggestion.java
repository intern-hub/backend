package com.internhub.backend.models;

import javax.persistence.*;

@Entity
@Table(name = "suggestions")
public class Suggestion {
    @GeneratedValue
    @Id
    @Column(name = "id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "content")
    private String content;

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

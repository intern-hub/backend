package com.internhub.backend.models;

import javax.persistence.*;

@Entity
@Table(name = "applications")
public class Application {
    @GeneratedValue
    @Id
    @Column(name = "id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
    @Column(name = "notes")
    private String notes;
    @Column(name = "applied")
    private boolean applied;
    @Column(name = "broken")
    private boolean broken;

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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isApplied() {
        return applied;
    }

    public void setApplied(boolean applied) {
        this.applied = applied;
    }

    public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }
}

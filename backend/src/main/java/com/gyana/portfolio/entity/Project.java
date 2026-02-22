package com.gyana.portfolio.entity;

import jakarta.persistence.*;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String techStack;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Project() {}

    public Project(String title, String description, String techStack, User user) {
        this.title = title;
        this.description = description;
        this.techStack = techStack;
        this.user = user;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTechStack() { return techStack; }
    public User getUser() { return user; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setTechStack(String techStack) { this.techStack = techStack; }
    public void setUser(User user) { this.user = user; }
}
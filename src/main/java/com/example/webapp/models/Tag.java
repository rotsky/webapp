package com.example.webapp.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;

    public Tag() {
    }

    public Tag(Long id, String tagName) {
        this.id = id;
        this.tagName = tagName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return this.tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}

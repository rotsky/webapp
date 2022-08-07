package com.example.webapp.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.UpdateTimestamp;


@Entity
public class Post {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title can't be empty.")
    @Size(min = 3, message = "A title must be at least 3 characters.")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Intro can't be empty")
    @Column(nullable = false, length = 200)
    private String intro;

    @NotBlank(message = "Article can't be empty")
    @Column(nullable = false, length = 3000)
    private String content;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_date")
    private Date create_date;

    @ManyToMany
    @JoinTable(
        name = "post_tags",
        joinColumns = @JoinColumn(name = "post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public Post(String title, String intro, String content){
        this.title = title;
        this.intro = intro;
        this.content = content;
    }

    public Post(String title, String intro, String content, List<Tag> tags){
        this.title = title;
        this.intro = intro;
        this.content = content;
        this.tags = tags;
    }

    public Post(){
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate(){
        return create_date;
    }

    public void setCreateDate(Date createDate){
        this.create_date = createDate;
    }

    public void setTags(List<Tag> tags){
        this.tags = tags;
    }

    public List<Tag> getTags(){
        return tags;
    }
}

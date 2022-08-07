package com.example.webapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.webapp.models.Post;
import com.example.webapp.models.Tag;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

    List<Post> findByTitleIsLike(String keyword);

    List<Post> findAllByTagsIn(List<Tag> tags);
}

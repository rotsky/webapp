package com.example.webapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.webapp.models.Post;

public interface PostRepository extends CrudRepository<Post, Long>{
    
}

package com.example.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp.models.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    
}

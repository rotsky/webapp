package com.example.webapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.webapp.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
    User findByUsername(String username);
    User findByEmail(String email);

}

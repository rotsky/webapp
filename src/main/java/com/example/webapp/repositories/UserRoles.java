package com.example.webapp.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.webapp.models.UserRole;

public interface UserRoles extends JpaRepository<UserRole, Long> {
    
    @Query("select ur.role from UserRole ur, User u where u.username=?1 and ur.userId = u.id")
    List<String> ofUserWith(String username);
}

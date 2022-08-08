package com.example.webapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.webapp.models.User;
import com.example.webapp.repositories.UserRepository;
import com.example.webapp.repositories.UserRoles;

@Service("customUserDetailsService")
public class UserDetailsLoader implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoles roles;

    @Autowired
    public UserDetailsLoader(UserRepository userRepository, UserRoles roles) {
        this.userRepository = userRepository;
        this.roles = roles;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("No user found for " + username);
        }

        List<String> userRoles = roles.ofUserWith(username);
        return new UserWithRoles(user, userRoles);
    }
}

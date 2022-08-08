package com.example.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.webapp.models.User;
import com.example.webapp.repositories.UserRepository;
import com.example.webapp.services.UserService;

@Controller
public class AuthenticationController {

   @Autowired
   private UserService userService;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Autowired
   private UserRepository userRepository;

   @GetMapping("/login")
   public String login(){
       return "login";
   }


   @PostMapping("/login")
   public String doLogin(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password){
       User dbUser = userRepository.findByUsername(username);
       if(dbUser != null && passwordEncoder.matches(password, dbUser.getPassword())) {
           userService.authenticate(dbUser);
           return "redirect:/";
       }
       return "/login";
   }

   @GetMapping("/signup")
   public String showSignUp(Model m){
       m.addAttribute("user", new User());
       return "signup";
   }
}
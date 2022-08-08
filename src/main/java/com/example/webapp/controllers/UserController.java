package com.example.webapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.webapp.models.User;
import com.example.webapp.models.UserRole;
import com.example.webapp.repositories.UserRepository;
import com.example.webapp.repositories.UserRoles;
import com.example.webapp.services.UserService;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoles userRoles;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public String saveUser(@Valid User user, Errors validation, Model m){

        String username = user.getUsername();
        User existingUsername = userRepository.findByUsername(username);
        User existingEmail = userRepository.findByEmail(user.getEmail());


        if(existingUsername != null){

            validation.rejectValue("username", "user.username", "Duplicated username " + username);

        }

        if(existingEmail != null){

            validation.rejectValue("email", "user.email", "Duplicated email " + user.getEmail());

        }

        if (validation.hasErrors()) {
            m.addAttribute("errors", validation);
            m.addAttribute("user", user);
            return "signup";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Custom validation if the username is taken

        User newUser = userRepository.save(user);

        UserRole ur = new UserRole();
        ur.setRole("ROLE_USER");
        ur.setUserId(newUser.getId());
        userRoles.save(ur);

        // Programmatic login after registering a user
        userService.authenticate(user);

        m.addAttribute("user", user);
        return "redirect:/";

    }

    // @GetMapping("/users/{id}")
    // public String showUser(@PathVariable Long id, Model viewModel){
    //     User user = userRepository.getOne(id);
    //     viewModel.addAttribute("user", user);
    //     viewModel.addAttribute("sessionUser", userService.loggedInUser());
    //     viewModel.addAttribute("showEditControls", userService.canEditProfile(user));
    //     return "users/show";
    // }

    // @GetMapping("/users/profile")
    // public String showProfile(Model viewModel){
    //     User logUser = userService.loggedInUser();

    //     if(logUser == null){
    //         viewModel.addAttribute("msg", "You need to be logged in to be able to see this page");
    //         return "error/custom";
    //     }

    //     return "redirect:/users/" + userService.loggedInUser().getId();
    // }

    // @GetMapping("/users/{id}/edit")
    // public String showEditForm(@PathVariable Long id, Model viewModel){
    //     User user = userRepository.getOne(id);
    //     viewModel.addAttribute("user", user);
    //     viewModel.addAttribute("showEditControls", userService.canEditProfile(user));
    //     return "users/edit";
    // }

    // @PostMapping("/users/{id}/edit")
    // public String editUser(@PathVariable Long id, @Valid User editedUser, Errors validation, Model m){

    //     editedUser.setId(id);

    //     if (validation.hasErrors()) {
    //         m.addAttribute("errors", validation);
    //         m.addAttribute("user", editedUser);
    //         m.addAttribute("showEditControls", checkEditAuth(editedUser));
    //         return "users/edit";
    //     }
    //     editedUser.setPassword(passwordEncoder.encode(editedUser.getPassword()));
    //     userRepository.save(editedUser);
    //     return "redirect:/users/"+id;
    // }

    // // Edit controls are being showed up if the user is logged in and it's the same user viewing the file
    // public Boolean checkEditAuth(User user){
    //     return userService.isLoggedIn() && (user.getId() == userService.loggedInUser().getId());
    // }


}
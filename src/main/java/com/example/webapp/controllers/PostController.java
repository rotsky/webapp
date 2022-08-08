package com.example.webapp.controllers;

import java.util.List;

import javax.validation.Valid;

// import java.util.ArrayList;
// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.webapp.models.Post;
import com.example.webapp.models.Tag;
import com.example.webapp.repositories.PostRepository;
import com.example.webapp.repositories.TagRepository;
import com.example.webapp.services.UserService;

@Controller
public class PostController {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/posts")
    public String posts(Model model){
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
		return "posts";
    }
    
    @GetMapping("/posts/create")
    public String getCreatePost(Model model){
      model.addAttribute("post", new Post());
      model.addAttribute("tags", tagRepository.findAll());
		  return "createPost";
    }
    
    @PostMapping("posts/create")
    public String createPost(@Valid Post postSubmitted, Errors validation, Model m, @RequestParam(name = "tags") List<Tag> tags){

        if (validation.hasErrors()) {
            m.addAttribute("errors", validation);
            System.out.println(validation.getAllErrors());
            m.addAttribute("post", postSubmitted);
            return "createPost";
        }

        postSubmitted.setTags(tags);
        postSubmitted.setUser(userService.loggedInUser());
        postRepository.save(postSubmitted);
        return "redirect:/posts";
    }

    @GetMapping("posts/{id}/edit")
    public String showEdit(@PathVariable Long id, Model m){
        Post post = postRepository.findById(id).get();
        if(!userService.isOwner(post.getUser())){
            return "redirect:/posts/" + id;
        }
        m.addAttribute("tags", tagRepository.findAll());
        m.addAttribute("post", post);
        return "postEdit";
    }

    @PostMapping("posts/{id}/edit")
    public String postEdit(@Valid Post postEdited, @RequestParam List<Tag> tags, Model model){
      Post postToBeUpdated = postRepository.findById(postEdited.getId()).get();
      postToBeUpdated.setTitle(postEdited.getTitle());
      postToBeUpdated.setIntro(postEdited.getIntro());
      postToBeUpdated.setContent(postEdited.getContent());
      postToBeUpdated.setTags(tags);
      postRepository.save(postToBeUpdated);
      return "redirect:/posts";
    }

    @GetMapping("/posts/{id}")
    public String postDetails(@PathVariable Long id, Model model){
        Post post = postRepository.findById(id).get();
        model.addAttribute("isOwner", userService.isOwner(post.getUser()));
        model.addAttribute("post", post);
		return "postDetails";
    }

    @PostMapping("/posts/{id}/delete")
    public String postDelete(@PathVariable Long id, Model model){
      Post post = postRepository.findById(id).orElseThrow();
      postRepository.delete(post);
      return "redirect:/posts";
    }


}

package com.example.webapp.controllers;

import java.util.List;

import javax.validation.Valid;

// import java.util.ArrayList;
// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.webapp.models.Post;
import com.example.webapp.models.Tag;
import com.example.webapp.repositories.PostRepository;
import com.example.webapp.repositories.TagRepository;

@Controller
public class PostController {
    
    @Autowired
    private PostRepository postRepository;

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
    model.addAttribute("tags", tagRepository.findAll());
		return "createPost";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam String title, @RequestParam String intro, @RequestParam String content, @RequestParam List<Tag> tags, Model model){
        Post post = new Post(title, intro, content, tags);
        postRepository.save(post);
        return "redirect:/posts";
    }

    @GetMapping("/posts/{id}")
    public String postDetails(@PathVariable Long id, Model model){
        Post post = postRepository.findById(id).get();
        // Optional<Post> temp = postRepository.findById(id);
        // ArrayList<Post> post = new ArrayList<>();
        // temp.ifPresent(post::add);
        model.addAttribute("post", post);
		return "postDetails";
    }

    @GetMapping("/posts/{id}/edit")
    public String getPostEdit(@PathVariable Long id, Model model){
      if(! postRepository.existsById(id)){
        return "redirect:/posts";
      }
      Post post = postRepository.findById(id).get();
      model.addAttribute("tags", tagRepository.findAll());
      model.addAttribute("post", post);
      return "postEdit";
    }

    @PostMapping("/posts/{id}/edit")
    public String postEdit(@Valid Post postEdited, @RequestParam List<Tag> tags, Model model){
      Post postToBeUpdated = postRepository.findById(postEdited.getId()).get();
      postToBeUpdated.setTitle(postEdited.getTitle());
      postToBeUpdated.setIntro(postEdited.getIntro());
      postToBeUpdated.setContent(postEdited.getContent());
      postToBeUpdated.setTags(tags);
      postRepository.save(postToBeUpdated);
      return "redirect:/posts";
    }

    @PostMapping("/posts/{id}/delete")
    public String postDelete(@PathVariable Long id, Model model){
      Post post = postRepository.findById(id).orElseThrow();
      postRepository.delete(post);
      return "redirect:/posts";
    }


}

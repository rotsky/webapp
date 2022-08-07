package com.example.webapp.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.webapp.models.Tag;
import com.example.webapp.repositories.PostRepository;
import com.example.webapp.repositories.TagRepository;

@Controller
public class SearchController {
    
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @GetMapping("/search")
    public String getSearch(Model model){
        return "search";
    }

    @PostMapping("/search")
    public String search(@RequestParam("searchRequest") String searchRequest, Model model){
        model.addAttribute("posts", postRepository.findByTitleIsLike('%' + searchRequest + '%'));
        return "search";
    }

    @GetMapping("/tag/{id}")
    public String showPostsByTag(@PathVariable Long id, Model model){
      List<Tag> tags = new ArrayList<>();
      tags.add(tagRepository.findById(id).get());
      model.addAttribute("posts", postRepository.findAllByTagsIn(tags));
      return "tagSearch";
    }
}

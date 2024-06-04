package ru.job4j.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dto.UserPostsDto;
import ru.job4j.model.Post;
import ru.job4j.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostsController {
    @Autowired
    private PostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Post> getAll() {
        return postService.findAll();
    }

    @GetMapping("/usersIds")
    @ResponseStatus(HttpStatus.OK)
    public List<UserPostsDto> getListDto(@RequestParam List<Integer> usersIds) {
        return postService.convert(usersIds);
    }
}

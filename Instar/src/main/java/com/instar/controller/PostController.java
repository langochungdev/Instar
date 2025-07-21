package com.instar.controller;

import com.instar.dto.PostDto;
import com.instar.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public PostDto create(@RequestBody PostDto dto) {
        return postService.create(dto);
    }

    @GetMapping("/{id}")
    public PostDto getById(@PathVariable Integer id) {
        return postService.findById(id);
    }

    @PutMapping("/{id}")
    public PostDto update(@PathVariable Integer id, @RequestBody PostDto dto) {
        return postService.update(id, dto);
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        postService.delete(id);
        return "Đã xóa bài viết";
    }

    @GetMapping
    public List<PostDto> getAll() {
        return postService.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<PostDto> getByUser(@PathVariable Integer userId) {
        return postService.findByUserId(userId);
    }
}

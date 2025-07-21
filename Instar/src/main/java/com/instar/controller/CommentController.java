package com.instar.controller;
import com.instar.dto.CommentDto;
import com.instar.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/post/{postId}")
    public CommentDto comment(@PathVariable Integer postId, @RequestBody CommentDto dto) {
        dto.setPostId(postId);
        return commentService.create(dto);
    }

    @GetMapping("/post/{postId}")
    public List<CommentDto> getByPost(@PathVariable Integer postId) {
        return commentService.findByPostId(postId);
    }

    @PostMapping("/{commentId}/reply")
    public CommentDto reply(@PathVariable Integer commentId, @RequestBody CommentDto dto) {
        dto.setParentId(commentId);
        return commentService.createReply(dto);
    }

    @PutMapping("/{id}")
    public CommentDto update(@PathVariable Integer id, @RequestBody CommentDto dto) {
        return commentService.update(id, dto);
    }


    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        commentService.delete(id);
        return "Đã xóa bình luận";
    }
}

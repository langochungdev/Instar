package com.instar.service;

import com.instar.dto.CommentDto;
import java.util.List;

public interface CommentService {
    CommentDto findById(Integer id);
    CommentDto create(CommentDto commentDto);
    CommentDto createReply(CommentDto dto);
    CommentDto update(Integer id, CommentDto commentDto);
    void delete(Integer id);
    List<CommentDto> findByPostId(Integer postId);
    List<CommentDto> findReplies(Integer commentId); // lấy các reply cho comment cha
}

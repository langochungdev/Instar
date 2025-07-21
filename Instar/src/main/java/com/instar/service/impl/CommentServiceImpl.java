package com.instar.service.impl;

import com.instar.dto.CommentDto;
import com.instar.entity.Comment;
import com.instar.entity.Post;
import com.instar.entity.User;
import com.instar.exception.CustomException;
import com.instar.mapper.CommentMapper;
import com.instar.repository.CommentRepository;
import com.instar.repository.PostRepository;
import com.instar.repository.UserRepository;
import com.instar.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDto findById(Integer id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto)
                .orElse(null);
    }

    @Override
    public CommentDto create(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId()).orElse(null);
        User user = userRepository.findById(commentDto.getUserId()).orElse(null);
        Comment parent = commentDto.getParentId() == null ? null : commentRepository.findById(commentDto.getParentId()).orElse(null);
        Comment comment = commentMapper.toEntity(commentDto, post, user, parent);
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDto update(Integer id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) return null;
        comment.setContent(commentDto.getContent());
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentDto> findByPostId(Integer postId) {
        return commentRepository.findAll().stream()
                .filter(c -> c.getPost().getId().equals(postId))
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> findReplies(Integer commentId) {
        return commentRepository.findAll().stream()
                .filter(c -> c.getParent() != null && c.getParent().getId().equals(commentId))
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto createReply(CommentDto dto) {
        // Lấy parent comment
        Comment parent = commentRepository.findById(dto.getParentId())
                .orElseThrow(() -> new CustomException("Parent comment không tồn tại"));
        // Lấy post liên quan
        Post post = postRepository.findById(dto.getPostId())
                .orElseThrow(() -> new CustomException("Post không tồn tại"));
        // Lấy user comment
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException("User không tồn tại"));

        // Chuyển sang entity đầy đủ
        Comment reply = commentMapper.toEntity(dto, post, user, parent);
        reply = commentRepository.save(reply);
        return commentMapper.toDto(reply);
    }


}

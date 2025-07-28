package com.instar.service.impl;

import com.instar.dto.CommentDto;
import com.instar.entity.Comment;
import com.instar.entity.Post;
import com.instar.entity.User;
import com.instar.exception.NoPermissionException;
import com.instar.mapper.CommentMapper;
import com.instar.repository.CommentRepository;
import com.instar.repository.PostRepository;
import com.instar.repository.UserRepository;
import com.instar.service.CommentService;
import com.instar.util.CurrentUserUtil;
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
    public CommentDto create(CommentDto dto) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!dto.getUserId().equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }

        Post post = postRepository.findById(dto.getPostId()).orElse(null);
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        Comment parent = dto.getParentId() == null ? null : commentRepository.findById(dto.getParentId()).orElse(null);
        Comment e = commentMapper.toEntity(dto, post, user, parent);
        e = commentRepository.save(e);
        return commentMapper.toDto(e);
    }

    @Override
    public CommentDto update(Integer id, CommentDto dto) {
        Comment e = commentRepository.findById(id).orElse(null);
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();

        if (e == null || (!e.getUser().getId().equals(currentUserId) && !admin)) {
            throw new NoPermissionException();
        }

        e.setContent(dto.getContent());
        e = commentRepository.save(e);
        return commentMapper.toDto(e);
    }

    @Override
    public void delete(Integer id) {
        Comment e = commentRepository.findById(id).orElse(null);
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();

        if (e == null || (!e.getUser().getId().equals(currentUserId) && !admin)) {
            throw new NoPermissionException();
        }

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
    public CommentDto createReply(CommentDto dto) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!dto.getUserId().equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }

        Comment parent = commentRepository.findById(dto.getParentId()).orElse(null);
        Post post = postRepository.findById(dto.getPostId()).orElse(null);
        User user = userRepository.findById(dto.getUserId()).orElse(null);

        Comment reply = commentMapper.toEntity(dto, post, user, parent);
        reply = commentRepository.save(reply);
        return commentMapper.toDto(reply);
    }

    @Override
    public List<CommentDto> findReplies(Integer commentId) {
        return commentRepository.findByParentId_Id(commentId).stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }
}

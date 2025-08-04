package com.instar.feature.post.model.postMedia;

import com.instar.common.exception.NoPermissionException;
import com.instar.feature.post.model.post.Post;
import com.instar.feature.post.model.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostMediaServiceImpl implements PostMediaService {
    private final PostMediaRepository postMediaRepository;
    private final PostRepository postRepository;
    private final PostMediaMapper postMediaMapper;

    @Override
    public PostMediaDto add(PostMediaDto dto) {
        Post post = postRepository.findById(dto.getPostId()).orElse(null);
        if (post == null) throw new NoPermissionException();
        PostMedia e = postMediaMapper.toEntity(dto, post);
        e = postMediaRepository.save(e);
        return postMediaMapper.toDto(e);
    }

    @Override
    public List<PostMediaDto> findByPostId(Integer postId) {
        return postMediaRepository.findByPostId(postId)
                .stream().map(postMediaMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        postMediaRepository.deleteById(id);
    }
}

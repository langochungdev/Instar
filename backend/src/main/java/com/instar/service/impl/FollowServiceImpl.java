package com.instar.service.impl;
import com.instar.dto.FollowDto;
import com.instar.entity.Follow;
import com.instar.entity.User;
import com.instar.exception.NoPermissionException;
import com.instar.mapper.FollowMapper;
import com.instar.repository.FollowRepository;
import com.instar.repository.UserRepository;
import com.instar.service.FollowService;
import com.instar.util.CurrentUserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final FollowMapper followMapper;

    @Override
    public FollowDto follow(Integer followerId, Integer followingId) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!followerId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        User follower = userRepository.findById(followerId).orElse(null);
        User following = userRepository.findById(followingId).orElse(null);
        Follow follow = Follow.builder()
                .follower(follower)
                .following(following)
                .createdAt(java.time.LocalDateTime.now())
                .build();
        follow = followRepository.save(follow);
        return followMapper.toDto(follow);
    }

    @Override
    public void unfollow(Integer followerId, Integer followingId) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!followerId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        followRepository.findAll().stream()
                .filter(f -> f.getFollower().getId().equals(followerId) && f.getFollowing().getId().equals(followingId))
                .findFirst()
                .ifPresent(f -> followRepository.deleteById(f.getId()));
    }

    @Override
    public List<FollowDto> getFollowers(Integer userId) {
        return followRepository.findAll().stream()
                .filter(f -> f.getFollowing().getId().equals(userId))
                .map(followMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<FollowDto> getFollowing(Integer userId) {
        return followRepository.findAll().stream()
                .filter(f -> f.getFollower().getId().equals(userId))
                .map(followMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isFollowing(Integer followerId, Integer followingId) {
        return followRepository.findAll().stream()
                .anyMatch(f -> f.getFollower().getId().equals(followerId) && f.getFollowing().getId().equals(followingId));
    }
}

package com.instar.feature.follow;
import java.util.List;

public interface FollowService {
    FollowDto follow(Integer followerId, Integer followingId);
    void unfollow(Integer followerId, Integer followingId);
    List<FollowDto> getFollowers(Integer userId);
    List<FollowDto> getFollowing(Integer userId);
    boolean isFollowing(Integer followerId, Integer followingId);
}

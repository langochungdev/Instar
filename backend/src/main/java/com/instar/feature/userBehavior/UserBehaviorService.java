package com.instar.feature.userBehavior;
import java.util.List;

public interface UserBehaviorService {
    UserBehaviorDto logBehavior(UserBehaviorDto behaviorDto);
    List<UserBehaviorDto> findByUserId(Integer userId);
}

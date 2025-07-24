package com.instar.service;
import com.instar.dto.UserBehaviorDto;
import java.util.List;

public interface UserBehaviorService {
    UserBehaviorDto logBehavior(UserBehaviorDto behaviorDto);
    List<UserBehaviorDto> findByUserId(Integer userId);
}

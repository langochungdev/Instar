package com.instar.feature.userBehavior;

import com.instar.common.exception.NoPermissionException;
import com.instar.common.util.CurrentUserUtil;
import com.instar.feature.user.User;
import com.instar.feature.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBehaviorServiceImpl implements UserBehaviorService {
    private final UserBehaviorRepository userBehaviorRepository;
    private final UserRepository userRepository;
    private final UserBehaviorMapper userBehaviorMapper;

    @Override
    public UserBehaviorDto logBehavior(UserBehaviorDto dto) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!dto.getUserId().equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        UserBehavior e = userBehaviorMapper.toEntity(dto, user);
        e = userBehaviorRepository.save(e);
        return userBehaviorMapper.toDto(e);
    }

    @Override
    public List<UserBehaviorDto> findByUserId(Integer userId) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!userId.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        return userBehaviorRepository.findAll().stream()
                .filter(b -> b.getUser().getId().equals(userId))
                .map(userBehaviorMapper::toDto)
                .collect(Collectors.toList());
    }
}

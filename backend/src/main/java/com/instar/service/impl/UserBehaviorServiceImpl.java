package com.instar.service.impl;
import com.instar.dto.UserBehaviorDto;
import com.instar.entity.UserBehavior;
import com.instar.entity.User;
import com.instar.mapper.UserBehaviorMapper;
import com.instar.repository.UserBehaviorRepository;
import com.instar.repository.UserRepository;
import com.instar.service.UserBehaviorService;
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
        User user = userRepository.findById(dto.getUserId()).orElse(null);
        UserBehavior e = userBehaviorMapper.toEntity(dto, user);
        e = userBehaviorRepository.save(e);
        return userBehaviorMapper.toDto(e);
    }

    @Override
    public List<UserBehaviorDto> findByUserId(Integer userId) {
        return userBehaviorRepository.findAll().stream()
                .filter(b -> b.getUser().getId().equals(userId))
                .map(userBehaviorMapper::toDto)
                .collect(Collectors.toList());
    }
}

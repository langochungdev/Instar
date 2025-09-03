package com.instar.feature.user.service;
import com.instar.feature.user.dto.UserDto;

import java.util.UUID;


public interface UserService {
    UserDto checkStatus(UUID userId);
    UserDto updateProfile(UUID userId, UserDto dto);

}

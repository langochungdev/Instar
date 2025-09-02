package com.instar.feature.user.service;

import com.instar.feature.user.dto.UserDto;

import java.util.UUID;

public interface UserService {
    void changePassword(UUID id, String oldPassword, String newPassword);
    void verifyAccount(UUID id, String code);
    UserDto checkStatus();
}

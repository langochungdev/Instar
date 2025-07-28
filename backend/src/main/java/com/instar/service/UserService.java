package com.instar.service;
import com.instar.dto.AuthRequest;
import com.instar.dto.AuthResponse;
import com.instar.dto.UserDto;
import com.instar.entity.User;

import java.util.List;

public interface UserService {
    UserDto findById(Integer id);
    UserDto create(UserDto userDto);
    UserDto update(Integer id, UserDto userDto);
    void delete(Integer id);
    List<User> findAll();
    UserDto findByUsername(String username);
    UserDto register(User e);
    AuthResponse login(AuthRequest request);
    void changePassword(Integer id, String oldPassword, String newPassword);
    void verifyAccount(Integer id, String code);
}

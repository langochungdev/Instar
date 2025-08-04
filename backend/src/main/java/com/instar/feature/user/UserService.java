package com.instar.feature.user;


import java.util.List;

public interface UserService {
    UserDto findById(Integer id);
    UserDto create(UserDto userDto);
    UserDto update(Integer id, UserDto userDto);
    void delete(Integer id);
    List<User> findAll();
    UserDto findByUsername(String username);
    void changePassword(Integer id, String oldPassword, String newPassword);
    void verifyAccount(Integer id, String code);
}

package com.instar.service.impl;
import com.instar.dto.AuthRequest;
import com.instar.dto.AuthResponse;
import com.instar.dto.UserDto;
import com.instar.entity.User;
import com.instar.exception.NoPermissionException;
import com.instar.mapper.UserMapper;
import com.instar.repository.UserRepository;
import com.instar.service.UserService;
import com.instar.util.CurrentUserUtil;
import com.instar.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

    @Override
    public UserDto create(UserDto dto) {
        boolean admin = CurrentUserUtil.isAdmin();
        if (!admin) {
            throw new NoPermissionException();
        }
        User e = userMapper.toEntity(dto);
        e = userRepository.save(e);
        return userMapper.toDto(e);
    }

    @Override
    public UserDto update(Integer id, UserDto dto) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!id.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        User e = userRepository.findById(id).orElse(null);
        if (e == null) return null;
        e.setFullName(dto.getFullName());
        e.setAvatarUrl(dto.getAvatarUrl());
        e.setBio(dto.getBio());
        e.setIsActive(dto.getIsActive());
        e.setIsVerified(dto.getIsVerified());
        e.setLastLogin(dto.getLastLogin());
        e.setRole(dto.getRole());
        e = userRepository.save(e);
        return userMapper.toDto(e);
    }

    @Override
    public void delete(Integer id) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!id.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        boolean admin = CurrentUserUtil.isAdmin();
        if (!admin) {
            throw new NoPermissionException();
        }
        return userRepository.findAll();
    }


    @Override
    public UserDto findByUsername(String username) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        User user = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(null);
        if (user == null) return null;
        if (!user.getId().equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        return userMapper.toDto(user);
    }

    @Override
    public UserDto register(User e) {
        e.setPassword(passwordEncoder.encode(e.getPassword()));
        e = userRepository.save(e);
        return userMapper.toDto(e);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null) throw new RuntimeException("User không tồn tại!");
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new RuntimeException("Sai mật khẩu!");
        String token = jwtUtil.createToken(user.getUsername());
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername());
        long expiresIn = jwtUtil.getExpiration();
        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .expiresIn(expiresIn)
                .build();
    }


    @Override
    public void changePassword(Integer id, String oldPassword, String newPassword) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!id.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return;
        if (!admin && !passwordEncoder.matches(oldPassword, user.getPassword())) return;
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void verifyAccount(Integer id, String code) {
        Integer currentUserId = CurrentUserUtil.getCurrentUserId();
        boolean admin = CurrentUserUtil.isAdmin();
        if (!id.equals(currentUserId) && !admin) {
            throw new NoPermissionException();
        }
        User user = userRepository.findById(id).orElse(null);
        if (user == null) return;
        user.setIsVerified(true);
        userRepository.save(user);
    }
}

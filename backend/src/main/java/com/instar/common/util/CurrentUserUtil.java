package com.instar.common.util;
import com.instar.infrastructure.security.CustomUserDetails;
import com.instar.feature.user.entity.User;
import com.instar.feature.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CurrentUserUtil {
    private final UserRepository userRepository;

    public CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            return (CustomUserDetails) principal;
        }
        return null;
    }

    public UUID getCurrentUserId() {
        CustomUserDetails user = getCurrentUser();
        System.out.println(user.getId());
        return user != null ? user.getId() : null;
    }

    public User getUser() {
        UUID userId = getCurrentUserId();
        return userId != null
                ? userRepository.findByIdWithRoles(userId).orElse(null)
                : null;
    }
}

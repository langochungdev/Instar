package com.instar.common.filter;
import com.instar.common.exception.errorcode.AuthError;
import com.instar.common.service.TokenBlacklistService;
import com.instar.common.util.JwtUtil;
import com.instar.infrastructure.security.CustomUserDetails;
import com.instar.feature.user.entity.User;
import com.instar.feature.user.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.instar.common.exception.BusinessException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Set<String> EXCLUDED_PATHS = Set.of(
            "/api/auth/logout",
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/refresh"
    );
    private final HandlerExceptionResolver resolver;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenBlacklistService tokenBlacklistService;

    public JwtAuthFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {
        try {
            System.out.println(request.getRequestURI());

            String path = request.getRequestURI();
            if (EXCLUDED_PATHS.stream().anyMatch(path::equalsIgnoreCase)) {
                filterChain.doFilter(request, response);
                return;
            }

//            String token = jwtUtil.getTokenFromCookie(request);
//            jwtUtil.validateOrThrow(token);

//            if (tokenBlacklistService.isTokenBlacklisted(token)) {
//                throw new BusinessException(AuthError.BLACKLISTED_TOKEN);
//            }

            String token = jwtUtil.getTokenBearer(request);
            jwtUtil.validateOrThrow(token);

            UUID userId = jwtUtil.extractUserId(token);
            if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userRepository.findById(userId).orElseThrow(() -> new BusinessException(AuthError.USER_NOT_FOUND));
                UserDetails userDetails = new CustomUserDetails(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail(),
                        user.getFullName(),
                        user.getIsActive(),
                        user.getIsVerified(),
                        user.getUserRoles().stream()
                                .map(ur -> new SimpleGrantedAuthority("ROLE_" + ur.getRole().getName()))
                                .toList()
                );


                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

            filterChain.doFilter(request, response);

        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            resolver.resolveException(request, response, null, ex);
        }
    }

}

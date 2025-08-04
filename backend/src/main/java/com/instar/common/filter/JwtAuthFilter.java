package com.instar.common.filter;

import com.instar.common.util.JwtUtil;
import com.instar.config.security.CustomUserDetails;
import com.instar.feature.user.User;
import com.instar.feature.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter { // chạy filter 1 lần mỗi request
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws IOException, ServletException {
        //authorization phần quyền
        String authorization = request.getHeader("Authorization");
        String token = null;
        String userId = null;

        // xác thực user, authorization phân quyền
        if (authorization != null && authorization.startsWith("Bearer ")) {
            System.out.println("Authorization header: " + authorization);
            token = authorization.substring(7).trim();
            if (jwtUtil.validateToken(token)) {
                userId = jwtUtil.extractUserId(token);
            } else {
                System.out.println("Token không hợp lệ hoặc đã hết hạn");
            }
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = userRepository.findById(Integer.valueOf(userId)).orElse(null);
            if (user != null) {
                UserDetails userDetails = new CustomUserDetails(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getIsActive(),
                        List.of(new SimpleGrantedAuthority(user.getRole())) // chứa tất cả role
                );
                System.out.println("request: username = " + user.getUsername() + ", role = " + user.getRole());

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities() // lấy quyền
                        );
                //lấy thông tin từ request nhét vào object securitycontext để chặn ip spam..
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}

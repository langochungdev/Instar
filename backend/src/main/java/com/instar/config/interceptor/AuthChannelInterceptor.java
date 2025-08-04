package com.instar.config.interceptor;//package com.instar.config.interceptor;
//
//import com.instar.entity.User;
//import com.instar.repository.UserRepository;
//import com.instar.util.JwtUtil;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.MessageChannel;
//import org.springframework.messaging.simp.stomp.StompCommand;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.messaging.support.ChannelInterceptor;
//import org.springframework.stereotype.Component;
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//public class AuthChannelInterceptor implements ChannelInterceptor {
//
//    private final JwtUtil jwtUtil;
//    private final UserRepository userRepository;
//
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            List<String> authHeaders = accessor.getNativeHeader("Authorization");
//            if (authHeaders != null && !authHeaders.isEmpty()) {
//                String token = authHeaders.get(0);
//                if (token.startsWith("Bearer ")) {
//                    token = token.substring(7);
//                }
//                if (jwtUtil.validateToken(token)) {
//                    String userId = jwtUtil.extractUserId(token);
//
//                    User user = userRepository.findById(Integer.valueOf(userId)).orElse(null);
//                    if (user != null) {
//                        accessor.setUser(() -> userId); // Cực kỳ quan trọng!
//                        System.out.println("WebSocket CONNECT: userId = " + userId + ", username = " + user.getUsername());
//                    } else {
//                        System.out.println("WebSocket CONNECT: userId không tồn tại trong DB");
//                    }
//                } else {
//                    System.out.println("WebSocket CONNECT: token không hợp lệ");
//                }
//            }
//        }
//        return message;
//    }
//}

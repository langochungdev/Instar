package com.instar.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // ep kết nối
                .setAllowedOriginPatterns("http://localhost:63342", "http://localhost:8080", "http://127.0.0.1:5500")
                .withSockJS();// bật hỗ trợ SockJS cho phép kết nối mà ko sợ brower ko hỗ trợ
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic/");// đăng ký kênh nhận tin topic/chat/1/user/2
        registry.setApplicationDestinationPrefixes("/app");
        // ep tin nhắn gửi lên có tiền tố là app sẽ gọi @MessageMapping (thuộc websocket) giống như get post
    }
}

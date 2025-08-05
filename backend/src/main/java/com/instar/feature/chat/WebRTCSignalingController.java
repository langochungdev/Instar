//package com.instar.feature.chat;
//
//import lombok.Data;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class WebRTCSignalingController {
//
//    @MessageMapping("/call")
//    @SendTo("/topic/call")
//    public SignalMessage signaling(@Payload SignalMessage message) {
//        // Đẩy message sang tất cả client đang lắng nghe /topic/call
//        return message;
//    }
//
//    // Định nghĩa message signaling: offer, answer, iceCandidate,...
//    @Data
//    public static class SignalMessage {
//        private String type; // offer, answer, candidate
//        private String from; // user gửi
//        private String to;   // user nhận (nếu muốn private, phía client lọc)
//        private String sdp;  // dữ liệu SDP cho offer/answer
//        private Object candidate; // ICE candidate
//    }
//}

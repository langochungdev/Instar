````markdown
### Hướng dẫn chuẩn frontend tích hợp chức năng chat realtime (ib & group) với backend Instar

---

#### 1. Chuẩn bị thông tin

- **userId:** id user đang đăng nhập (lấy từ backend khi login).
- **chatId:** id phòng chat (ib hoặc group, lấy từ API backend).
- **MessageDto:** gồm các trường: senderId, chatId, content, imageUrl, videoUrl, createdAt,...

---

#### 2. Kết nối WebSocket (SockJS + StompJS)

```js
const socket = new SockJS("http://localhost:8080/ws");
const stompClient = Stomp.over(socket);

stompClient.connect({}, function () {
  // Subscribe vào kênh chat riêng của user trong phòng chat (group hoặc ib đều như nhau)
  const topic = "/topic/chat/" + chatId + "/user/" + userId;
  stompClient.subscribe(topic, function (msg) {
    const data = JSON.parse(msg.body);
    // data là 1 MessageDto (tin nhắn mới), hiển thị lên UI hoặc xử lý tùy ý
  });
});
```
````

---

#### 3. Gửi tin nhắn mới

```js
const message = {
  senderId: userId, // id user gửi
  chatId: chatId, // id phòng chat
  content: "Nội dung gửi", // text message
  // imageUrl, videoUrl (nếu có, optional)
};
stompClient.send("/app/chat.send", {}, JSON.stringify(message));
```

---

#### 4. Nhận tin nhắn realtime

- Chỉ cần subscribe đúng channel `/topic/chat/{chatId}/user/{userId}` như trên,
- Bất cứ ai gửi vào chat này (group hoặc ib) đều nhận realtime nếu là thành viên phòng chat.

---

#### 5. Lấy lịch sử tin nhắn cũ

```js
// Gọi REST API lấy lịch sử cũ của 1 phòng chat
GET http://localhost:8080/api/messages/conversations/{chatId}
```

- Kết quả trả về: list các MessageDto (tin nhắn cũ của phòng chat này).

---

#### 6. Lưu ý quan trọng

- **userId và chatId phải đúng và hợp lệ** (user phải là thành viên chat đó).
- Nếu test ngoài không gửi JWT, backend phải tạm thời bỏ check xác thực user với WebSocket (hoặc chỉ kiểm tra khi currentUserId != null).
- Muốn bảo mật full như REST, cần truyền JWT khi connect WebSocket (qua header hoặc param, cần backend xử lý thêm).

---

#### 7. Giao diện gợi ý cho frontend

- **Sidebar:** Danh sách phòng chat (group/ib), hiện tên group hoặc tên user còn lại, lấy từ API backend.
- **Main panel:** Hiện tin nhắn (list), input gửi, nhận tin realtime qua subscribe topic như trên.
- Khi user chọn 1 chatId: gọi API lấy lịch sử + subscribe topic chat đó.

---

#### 8. Demo luồng sử dụng chuẩn

1. User đăng nhập (nhận về userId).
2. Gọi API lấy list phòng chat: `/api/user/chats/{userId}` (trả về các chatId).
3. User chọn 1 phòng chat → lấy chatId.
4. Kết nối websocket, subscribe vào `/topic/chat/{chatId}/user/{userId}`.
5. Gọi API lấy lịch sử tin nhắn cũ.
6. Gửi và nhận tin nhắn realtime qua WebSocket.

---

#### 9. Ví dụ thực tế đơn giản (JS thuần)

```js
// 1. Kết nối
let socket = new SockJS("http://localhost:8080/ws");
let stompClient = Stomp.over(socket);
stompClient.connect({}, function () {
  stompClient.subscribe(
    "/topic/chat/" + chatId + "/user/" + userId,
    function (msg) {
      let data = JSON.parse(msg.body);
      // Hiển thị data.content lên UI...
    }
  );
});

// 2. Gửi tin nhắn
function sendMsg() {
  let msg = {
    senderId: userId,
    chatId: chatId,
    content: "Hello group!",
  };
  stompClient.send("/app/chat.send", {}, JSON.stringify(msg));
}
```

---

#### 10. Tổng kết

- Muốn dùng chat group/ib chỉ cần:

  - **Biết userId và chatId**
  - **Subscribe đúng topic**
  - **Gửi tin nhắn đúng format MessageDto**

- Nếu muốn bảo mật nâng cao với JWT, cần truyền token khi connect websocket và custom backend thêm (liên hệ để được hướng dẫn chi tiết).

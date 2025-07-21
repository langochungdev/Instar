# Instar

# 📸 Dự Án Clone Instagram – Spring Boot RESTful API + ReactJS

## **Mục tiêu dự án**

- Xây dựng một hệ thống clone Instagram đầy đủ chức năng: đăng ký, đăng nhập đa thiết bị, đăng bài, like, share, comment, lưu, nhắn tin realtime, gọi video/WebRTC, phân tích hành vi, gợi ý bài viết.
- Backend sử dụng **Spring Boot** (JDK 21, Maven, SQL Server).
- Frontend sử dụng **ReactJS** + Bootstrap.
- Tối ưu cho học tập, triển khai thực tế trên Ubuntu server, có CI/CD, kiểm thử tự động với JUnit.

---

## **Tính năng chính**

1. **Đăng ký, đăng nhập, xác thực tài khoản, đổi mật khẩu, quản lý đa thiết bị.**
2. **Đăng bài, sửa, xóa, tải xuống bài viết (ảnh/video).**
3. **Tương tác: like, share, comment, trả lời comment, lưu bài viết.**
4. **Hệ thống follow, followers, following.**
5. **Nhắn tin realtime (WebSocket), quản lý hội thoại, thông báo, gọi video WebRTC, chia sẻ màn hình.**
6. **Phân tích hành vi, gợi ý bài viết theo thói quen người dùng.**
7. **Quản lý thông báo, ghi log hoạt động, bảo mật, triển khai đa môi trường, CI/CD tự động.**

---

## **Kiến trúc tổng thể**

### **1. Backend (Spring Boot)**

- **Kiến trúc:** RESTful API, phân lớp (controller, service, repository, entity, config).
- **Bảo mật:** Spring Security + JWT, hỗ trợ login nhiều thiết bị, refresh token.
- **Realtime:** WebSocket cho nhắn tin, signaling server cho WebRTC.
- **Database:** SQL Server, thiết kế chuẩn hóa, quan hệ chặt chẽ giữa các bảng.

### **2. Frontend (ReactJS + Bootstrap)**

- **Layout:** Clone gần giống Instagram, responsive, UI/UX tối ưu.
- **Giao tiếp backend:** Sử dụng Axios fetch API, JWT lưu ở localStorage.
- **Realtime:** Nhắn tin/gọi video qua WebSocket/WebRTC.
- **Phân trang, tìm kiếm, filter, thông báo realtime.**

---

## **Luồng chức năng chính (API Routing)**

### **User APIs**

| Chức năng          | Method          | Đường dẫn                                     | Mô tả                         |
| ------------------ | --------------- | --------------------------------------------- | ----------------------------- |
| Đăng ký            | POST            | `/api/users/register`                         | Tạo tài khoản mới             |
| Đăng nhập          | POST            | `/api/users/login`                            | Đăng nhập, nhận token         |
| Lấy thông tin user | GET             | `/api/users/{id}`                             | Lấy chi tiết user             |
| Cập nhật user      | PUT             | `/api/users/{id}`                             | Sửa thông tin                 |
| Đổi mật khẩu       | PUT             | `/api/users/{id}/password`                    | Đổi mật khẩu                  |
| Xác thực tài khoản | POST            | `/api/users/{id}/verify`                      | Kích hoạt account             |
| Quản lý thiết bị   | GET/POST/DELETE | `/api/devices`, `/api/users/{userId}/devices` | Quản lý đăng nhập đa thiết bị |

### **Post APIs**

| Chức năng     | Method | Đường dẫn                                 | Mô tả                      |
| ------------- | ------ | ----------------------------------------- | -------------------------- |
| Đăng bài      | POST   | `/api/posts`                              | Tạo bài đăng mới           |
| Xem bài       | GET    | `/api/posts/{id}`                         | Xem chi tiết bài viết      |
| Sửa bài       | PUT    | `/api/posts/{id}`                         | Chỉnh sửa                  |
| Xóa bài       | DELETE | `/api/posts/{id}`                         | Xóa/ẩn bài viết            |
| Danh sách bài | GET    | `/api/posts`, `/api/users/{userId}/posts` | Liệt kê bài, lọc theo user |

### **Comment APIs**

| Chức năng     | Method     | Đường dẫn                         | Mô tả           |
| ------------- | ---------- | --------------------------------- | --------------- |
| Bình luận     | POST       | `/api/posts/{postId}/comments`    | Bình luận       |
| Xem bình luận | GET        | `/api/posts/{postId}/comments`    | Xem bình luận   |
| Trả lời       | POST       | `/api/comments/{commentId}/reply` | Trả lời comment |
| Sửa/Xóa       | PUT/DELETE | `/api/comments/{id}`              | Sửa hoặc xóa    |

### **Like, Follow, Save APIs**

- Like: `/api/posts/{postId}/like` (POST/DELETE/GET)
- Follow: `/api/users/{userId}/follow` (POST/DELETE/GET)
- Save: `/api/posts/{postId}/save` (POST/DELETE/GET)

### **Message & Notification APIs**

- Gửi tin nhắn: `POST /api/messages`
- Danh sách hội thoại: `GET /api/messages/conversations`
- Đánh dấu đã đọc: `PUT /api/messages/{id}/read`
- Thông báo: `GET /api/users/{userId}/notifications`, `PUT /api/notifications/{id}/read`

### **User Behavior API**

- Ghi log hành vi: `POST /api/user-behaviors`
- Lấy hành vi: `GET /api/users/{userId}/behaviors`

---

## **Thiết kế Cơ sở dữ liệu (Database Schema)**

### **1. Users**

- `id` (PK), `username`, `email`, `password`, `fullname`, `avatar_url`, `bio`, `created_at`, `last_login`, `is_active`, `is_verified`

### **2. Posts**

- `id` (PK), `user_id` (FK), `content`, `image_url`, `video_url`, `created_at`, `updated_at`, `is_deleted`

### **3. Comments**

- `id` (PK), `post_id` (FK), `user_id` (FK), `content`, `created_at`, `parent_id` (FK)

### **4. Likes**

- `id` (PK), `post_id` (FK), `user_id` (FK), `created_at`

### **5. Follows**

- `id` (PK), `follower_id` (FK), `following_id` (FK), `created_at`

### **6. Saved_Posts**

- `id` (PK), `user_id` (FK), `post_id` (FK), `created_at`

### **7. Messages**

- `id` (PK), `sender_id` (FK), `receiver_id` (FK), `content`, `image_url`, `video_url`, `created_at`, `is_read`

### **8. Devices**

- `id` (PK), `user_id` (FK), `device_token`, `device_name`, `last_login`, `is_active`

### **9. Notifications**

- `id` (PK), `user_id` (FK), `type`, `message`, `link`, `is_read`, `created_at`

### **10. User_Behaviors**

- `id` (PK), `user_id` (FK), `action`, `target_id`, `created_at`

> **Chú thích:** FK = khóa ngoại (foreign key), PK = khóa chính (primary key)

---

## **Quy trình triển khai dự án**

### **1. Khởi tạo và cấu hình**

- Khởi tạo project Spring Boot (Maven, JDK 21, SQL Server).
- Tạo cấu trúc thư mục theo chuẩn.
- Thêm dependency: Spring Data JPA, Security, JWT, WebSocket, Lombok, JUnit.
- Khởi tạo ReactJS, cấu hình Bootstrap, cài đặt Axios.

### **2. Xây dựng từng module backend**

- Tạo các entity, repository, service, controller cho từng bảng/chức năng.
- Cấu hình bảo mật, JWT, đa thiết bị.
- Tích hợp WebSocket, signaling cho WebRTC.

### **3. Xây dựng frontend**

- Thiết kế giao diện, tạo các trang (login, feed, profile, chat, call, ...).
- Gọi REST API từ backend.
- Kết nối WebSocket, WebRTC.
- Xử lý lưu trữ token, refresh, đa thiết bị.

### **4. Kiểm thử**

- Viết test JUnit cho service, repository, controller.
- Test frontend bằng tay, tự động hóa nếu cần.

### **5. Triển khai (deploy)**

- Dockerize cả backend và frontend.
- Đẩy code lên Github.
- Thiết lập CI/CD (Github Actions/Jenkins).
- Triển khai lên Ubuntu server.
- Cấu hình reverse proxy (nginx).

### **6. Vận hành & bảo trì**

- Theo dõi log, alert, backup dữ liệu định kỳ.
- Vá lỗi, tối ưu, bổ sung chức năng khi cần.

---

## **Yêu cầu công nghệ**

- **Backend:** Spring Boot (JDK 21), Maven, SQL Server, WebSocket, JWT, JUnit.
- **Frontend:** ReactJS, Bootstrap, Axios, WebRTC.
- **DevOps:** Docker, Github, CI/CD, Ubuntu, nginx.

---

## **Lưu ý**

- Mọi API đều chuẩn REST, có phân quyền, mã hóa JWT, validate dữ liệu đầu vào/đầu ra.
- Để build dự án cần cài Java 21, NodeJS, SQL Server, Docker.
- Dự án phù hợp cho cả thực hành và triển khai thật, code tổ chức rõ ràng, có thể mở rộng dễ dàng.

---

**Bạn chỉ cần thực hiện theo từng bước, mỗi module đều độc lập, có thể phát triển song song. Nếu gặp khó khăn, đọc lại phần API, DB, hoặc hỏi AI này để được hướng dẫn chi tiết từng file, từng chức năng.**

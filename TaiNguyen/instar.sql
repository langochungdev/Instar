CREATE DATABASE instar;
--GO
USE instar;
--GO
-- Bảng users
CREATE TABLE users (
    id INT IDENTITY PRIMARY KEY,
    username NVARCHAR(50) UNIQUE NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    password NVARCHAR(100) NOT NULL,
    full_name NVARCHAR(100) NOT NULL,
    avatar_url NVARCHAR(255) NULL,
    bio NVARCHAR(255) NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    last_login DATETIME NULL,
    is_active BIT NOT NULL DEFAULT 1,
    is_verified BIT NOT NULL DEFAULT 0,
    role NVARCHAR(20) NOT NULL DEFAULT 'USER'
);

CREATE TABLE chats (
    id INT IDENTITY PRIMARY KEY,
    chat_name NVARCHAR(100) NULL,
    is_group BIT NOT NULL DEFAULT 0,
    created_by INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE chat_users (
    chat_id INT NOT NULL,
    user_id INT NOT NULL,
    is_admin BIT NOT NULL DEFAULT 0,
    PRIMARY KEY (chat_id, user_id),
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE messages (
    id INT IDENTITY PRIMARY KEY,
    chat_id INT NOT NULL,
    sender_id INT NOT NULL,
    content NVARCHAR(500) NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    is_read BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES users(id)
);

CREATE TABLE message_attachments (
    id INT IDENTITY PRIMARY KEY,
    message_id INT NOT NULL,
    file_url NVARCHAR(255) NOT NULL,
    file_type NVARCHAR(50) NOT NULL,   -- 'image', 'video', 'pdf', 'other'
    FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE
);

-- Bảng posts giữ thông tin bài đăng cơ bản
CREATE TABLE posts (
    id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    content NVARCHAR(500) NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME NULL,
    is_deleted BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Bảng post_media lưu file ảnh/video liên quan tới bài đăng
CREATE TABLE post_media (
    id INT IDENTITY PRIMARY KEY,
    post_id INT NOT NULL,
    media_url NVARCHAR(255) NOT NULL,
    media_type NVARCHAR(50) NOT NULL, -- 'image' hoặc 'video'
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

CREATE TABLE comments (
    id INT IDENTITY PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    content NVARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    parent_id INT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (parent_id) REFERENCES comments(id)
);

CREATE TABLE likes (
    id INT IDENTITY PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) 
);

CREATE TABLE follows (
    id INT IDENTITY PRIMARY KEY,
    follower_id INT NOT NULL,
    following_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (follower_id) REFERENCES users(id),
    FOREIGN KEY (following_id) REFERENCES users(id) 
);

CREATE TABLE saved_posts (
    id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);


CREATE TABLE devices (
    id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    device_token NVARCHAR(255) NULL,
    device_name NVARCHAR(100) NULL,
    fingerprint NVARCHAR(255) NULL,
    last_login DATETIME NOT NULL DEFAULT GETDATE(),
    is_active BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE notifications (
    id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    type NVARCHAR(50) NOT NULL,
    message NVARCHAR(255) NOT NULL,
    link NVARCHAR(255) NULL,
    is_read BIT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE user_behaviors (
    id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    action NVARCHAR(50) NOT NULL,
    target_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- USERS
INSERT INTO users (username, email, password, full_name, avatar_url, bio, created_at, is_active, is_verified, role) VALUES
('admin', 'admin@email.com', '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS', N'La Hung Admin', NULL, N'Chào mọi người!', GETDATE(), 1, 1, 'ADMIN'),
('user', 'user@email.com', '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS', N'La Hung user', NULL, N'Yêu du lịch', GETDATE(), 1, 1, 'USER'),
('user0', 'user0@email.com', '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS', N'La Hung user', NULL, NULL, GETDATE(), 1, 0, 'USER'),
('david', 'david@email.com', '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS', N'David Hồ', NULL, N'Lập trình viên', GETDATE(), 1, 1, 'USER'),
('emma', 'emma@email.com', '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS', N'Emma Phạm', NULL, N'Thích đọc sách', GETDATE(), 1, 1, 'USER'),
('frank', 'frank@email.com', '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS', N'Frank Võ', NULL, NULL, GETDATE(), 1, 0, 'USER'),
('grace', 'grace@email.com', '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS', N'Grace Đặng', NULL, N'Sống ảo 100%', GETDATE(), 1, 1, 'USER'),
('henry', 'henry@email.com', '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS', N'Henry Vũ', NULL, NULL, GETDATE(), 1, 0, 'USER'),
('ivy', 'ivy@email.com', '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS', N'Ivy Hồ', NULL, N'Tôi là Ivy', GETDATE(), 1, 1, 'USER'),
('jack', 'jack@email.com', '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS', N'Jack Nguyễn', NULL, NULL, GETDATE(), 1, 1, 'USER');

-- CHATS
INSERT INTO chats (chat_name, is_group, created_by, created_at) VALUES
(NULL, 0, 1, GETDATE()),
(NULL, 0, 2, GETDATE()),
(N'Nhóm bạn thân', 1, 1, GETDATE()),
(N'Dự án Instar', 1, 4, GETDATE());

-- CHAT_USERS
INSERT INTO chat_users (chat_id, user_id, is_admin) VALUES
(1, 1, 0),
(1, 2, 0),
(2, 2, 0),
(2, 3, 0),
(3, 1, 1),
(3, 2, 0),
(3, 4, 0),
(3, 5, 0),
(4, 4, 1),
(4, 5, 0),
(4, 6, 0),
(4, 7, 0);

-- POSTS
INSERT INTO posts (user_id, content, created_at, is_deleted) VALUES
(1, N'Bài đầu tiên của mình!', GETDATE(), 0),
(2, N'Trời hôm nay thật đẹp', GETDATE(), 0),
(3, N'Check-in Đà Lạt', GETDATE(), 0),
(4, N'Góc học tập của mình', GETDATE(), 0),
(5, N'Tôi yêu sách', GETDATE(), 0),
(6, N'Đi biển vui quá', GETDATE(), 0),
(7, N'Chill cùng bạn bè', GETDATE(), 0),
(8, N'Hôm nay ăn gì nhỉ?', GETDATE(), 0),
(9, N'Bức ảnh kỷ niệm', GETDATE(), 0),
(10, N'Happy weekend!', GETDATE(), 0);

-- POST_MEDIA
INSERT INTO post_media (post_id, media_url, media_type) VALUES
(1, 'img1.jpg', 'image'),
(2, 'img2.jpg', 'image'),
(3, 'img3.jpg', 'image'),
(4, 'img4.jpg', 'image'),
(5, 'img5.jpg', 'image'),
(6, 'img6.jpg', 'image'),
(7, 'img7.jpg', 'image'),
(8, 'img8.jpg', 'image'),
(9, 'img9.jpg', 'image'),
(10, 'img10.jpg', 'image'),
(3, 'video1.mp4', 'video'),
(4, 'video2.mp4', 'video'),
(5, 'img5_1.jpg', 'image'),
(5, 'img5_2.jpg', 'image');

-- COMMENTS
INSERT INTO comments (post_id, user_id, content, created_at) VALUES
(1, 2, N'Chào bạn!', GETDATE()),
(1, 3, N'Tuyệt vời quá!', GETDATE()),
(2, 1, N'Cảnh đẹp quá', GETDATE()),
(2, 4, N'Bạn chụp bằng máy gì vậy?', GETDATE()),
(3, 5, N'Mình cũng mới đi Đà Lạt xong', GETDATE()),
(4, 6, N'Học chăm quá!', GETDATE()),
(5, 7, N'Sách gì vậy bạn?', GETDATE()),
(6, 8, N'Ở đâu đó bạn?', GETDATE()),
(7, 9, N'Nhóm này vui ghê', GETDATE()),
(8, 10, N'Món gì ngon vậy?', GETDATE());

-- LIKES
INSERT INTO likes (post_id, user_id, created_at) VALUES
(1, 2, GETDATE()), (1, 3, GETDATE()), (2, 1, GETDATE()), (2, 4, GETDATE()), (3, 5, GETDATE()),
(4, 6, GETDATE()), (5, 7, GETDATE()), (6, 8, GETDATE()), (7, 9, GETDATE()), (8, 10, GETDATE());

-- FOLLOWS
INSERT INTO follows (follower_id, following_id, created_at) VALUES
(1, 2, GETDATE()), (2, 3, GETDATE()), (3, 4, GETDATE()), (4, 5, GETDATE()), (5, 6, GETDATE()),
(6, 7, GETDATE()), (7, 8, GETDATE()), (8, 9, GETDATE()), (9, 10, GETDATE()), (10, 1, GETDATE());

-- SAVED_POSTS
INSERT INTO saved_posts (user_id, post_id, created_at) VALUES
(1, 2, GETDATE()), (2, 3, GETDATE()), (3, 4, GETDATE()), (4, 5, GETDATE()), (5, 6, GETDATE()),
(6, 7, GETDATE()), (7, 8, GETDATE()), (8, 9, GETDATE()), (9, 10, GETDATE()), (10, 1, GETDATE());

-- MESSAGES (không có file_url/file_type)
INSERT INTO messages (chat_id, sender_id, content, created_at, is_read) VALUES
(1, 1, N'Hello user!', GETDATE(), 1),
(1, 2, N'Chào admin!', GETDATE(), 1),
(2, 2, N'Hello user0!', GETDATE(), 0),
(2, 3, N'Chào user!', GETDATE(), 0),
(3, 1, N'Chào cả nhóm!', GETDATE(), 0),
(3, 2, N'Hello mọi người!', GETDATE(), 0),
(3, 4, N'Có ai đi cafe không?', GETDATE(), 0),
(3, 5, N'Đi chứ!', GETDATE(), 0),
(4, 4, N'Dự án Instar tiến độ tới đâu rồi?', GETDATE(), 0),
(4, 5, N'Mình mới làm xong phần backend!', GETDATE(), 0),
(4, 6, N'Frontend ok chưa mọi người?', GETDATE(), 0),
(4, 7, N'Mai họp nhé', GETDATE(), 0);

-- MESSAGE_ATTACHMENTS (gắn file cho message_id bất kỳ)
INSERT INTO message_attachments (message_id, file_url, file_type) VALUES
(1, 'chat_img1.jpg', 'image'),
(2, 'tailieu.pdf', 'pdf'),
(5, 'video_group.mp4', 'video'),
(5, 'hinhanh_group.png', 'image'),
(8, 'docx1.docx', 'other');

INSERT INTO devices (user_id, device_token, device_name, fingerprint, last_login, is_active) VALUES
(1, 'token1', N'iPhone 13', 'fp1', GETDATE(), 1),
(2, 'token2', N'Galaxy S21', 'fp2', GETDATE(), 1),
(3, 'token3', N'Macbook Pro', 'fp3', GETDATE(), 1),
(4, 'token4', N'Laptop HP', 'fp4', GETDATE(), 1),
(5, 'token5', N'iPad Air', 'fp5', GETDATE(), 1),
(6, 'token6', N'Xiaomi Note', 'fp6', GETDATE(), 1),
(7, 'token7', N'Dell XPS', 'fp7', GETDATE(), 1),
(8, 'token8', N'Samsung Tab', 'fp8', GETDATE(), 1),
(9, 'token9', N'Asus Zenbook', 'fp9', GETDATE(), 1),
(10, 'token10', N'Lenovo Yoga', 'fp10', GETDATE(), 1);

-- NOTIFICATIONS
INSERT INTO notifications (user_id, type, message, link, is_read, created_at) VALUES
(1, 'like', N'Bob đã thích bài viết của bạn', NULL, 0, GETDATE()),
(2, 'comment', N'Alice đã bình luận bài viết của bạn', NULL, 0, GETDATE()),
(3, 'follow', N'David vừa theo dõi bạn', NULL, 0, GETDATE()),
(4, 'like', N'Emma đã thích bài viết của bạn', NULL, 0, GETDATE()),
(5, 'comment', N'Frank đã bình luận bài viết của bạn', NULL, 0, GETDATE()),
(6, 'follow', N'Grace vừa theo dõi bạn', NULL, 0, GETDATE()),
(7, 'like', N'Henry đã thích bài viết của bạn', NULL, 0, GETDATE()),
(8, 'comment', N'Ivy đã bình luận bài viết của bạn', NULL, 0, GETDATE()),
(9, 'follow', N'Jack vừa theo dõi bạn', NULL, 0, GETDATE()),
(10, 'like', N'Alice đã thích bài viết của bạn', NULL, 0, GETDATE());

-- USER_BEHAVIORS
INSERT INTO user_behaviors (user_id, action, target_id, created_at) VALUES
(1, 'view_post', 2, GETDATE()),
(2, 'like_post', 3, GETDATE()),
(3, 'comment_post', 4, GETDATE()),
(4, 'save_post', 5, GETDATE()),
(5, 'share_post', 6, GETDATE()),
(6, 'search', 7, GETDATE()),
(7, 'view_profile', 8, GETDATE()),
(8, 'like_post', 9, GETDATE()),
(9, 'follow_user', 10, GETDATE()),
(10, 'view_post', 1, GETDATE());


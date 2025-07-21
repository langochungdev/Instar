CREATE DATABASE instar;
GO
USE instar;
GO

-- Bảng Users
CREATE TABLE Users (
    id INT IDENTITY PRIMARY KEY,
    username NVARCHAR(50) UNIQUE NOT NULL,
    email NVARCHAR(100) UNIQUE NOT NULL,
    password NVARCHAR(100) NOT NULL,
    fullname NVARCHAR(100) NOT NULL,
    avatar_url NVARCHAR(255) NULL,
    bio NVARCHAR(255) NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    last_login DATETIME NULL,
    is_active BIT NOT NULL DEFAULT 1,
    is_verified BIT NOT NULL DEFAULT 0
);

-- Bảng Posts
CREATE TABLE Posts (
    id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    content NVARCHAR(500) NULL,
    image_url NVARCHAR(255) NULL,
    video_url NVARCHAR(255) NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    updated_at DATETIME NULL,
    is_deleted BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Bảng Comments
CREATE TABLE Comments (
    id INT IDENTITY PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    content NVARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    parent_id INT NULL,
    FOREIGN KEY (post_id) REFERENCES Posts(id),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (parent_id) REFERENCES Comments(id)
);

-- Bảng Likes
CREATE TABLE Likes (
    id INT IDENTITY PRIMARY KEY,
    post_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (post_id) REFERENCES Posts(id),
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Bảng Follows
CREATE TABLE Follows (
    id INT IDENTITY PRIMARY KEY,
    follower_id INT NOT NULL,
    following_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (follower_id) REFERENCES Users(id),
    FOREIGN KEY (following_id) REFERENCES Users(id)
);

-- Bảng Saved_Posts
CREATE TABLE Saved_Posts (
    id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    post_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(id),
    FOREIGN KEY (post_id) REFERENCES Posts(id)
);

-- Bảng Messages
CREATE TABLE Messages (
    id INT IDENTITY PRIMARY KEY,
    sender_id INT NOT NULL,
    receiver_id INT NOT NULL,
    content NVARCHAR(500) NULL,
    image_url NVARCHAR(255) NULL,
    video_url NVARCHAR(255) NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    is_read BIT NOT NULL DEFAULT 0,
    FOREIGN KEY (sender_id) REFERENCES Users(id),
    FOREIGN KEY (receiver_id) REFERENCES Users(id)
);

-- Bảng Devices
CREATE TABLE Devices (
    id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    device_token NVARCHAR(255) NOT NULL,
    device_name NVARCHAR(100) NOT NULL,
    last_login DATETIME NOT NULL DEFAULT GETDATE(),
    is_active BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Bảng Notifications
CREATE TABLE Notifications (
    id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    type NVARCHAR(50) NOT NULL,
    message NVARCHAR(255) NOT NULL,
    link NVARCHAR(255) NULL,
    is_read BIT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(id)
);

-- Bảng User_Behaviors
CREATE TABLE User_Behaviors (
    id INT IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    action NVARCHAR(50) NOT NULL,
    target_id INT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT GETDATE(),
    FOREIGN KEY (user_id) REFERENCES Users(id)
);



-- Insert Users
INSERT INTO Users (username, email, password, fullname, avatar_url, bio, created_at, is_active, is_verified) VALUES
('alice', 'alice@email.com', 'pass123', N'Alice Nguyễn', NULL, N'Chào mọi người!', GETDATE(), 1, 1),
('bob', 'bob@email.com', 'pass123', N'Bob Trần', NULL, N'Yêu du lịch', GETDATE(), 1, 0),
('charlie', 'charlie@email.com', 'pass123', N'Charlie Lê', NULL, NULL, GETDATE(), 1, 1),
('david', 'david@email.com', 'pass123', N'David Hồ', NULL, N'Lập trình viên', GETDATE(), 1, 1),
('emma', 'emma@email.com', 'pass123', N'Emma Phạm', NULL, N'Thích đọc sách', GETDATE(), 1, 1),
('frank', 'frank@email.com', 'pass123', N'Frank Võ', NULL, NULL, GETDATE(), 1, 0),
('grace', 'grace@email.com', 'pass123', N'Grace Đặng', NULL, N'Sống ảo 100%', GETDATE(), 1, 1),
('henry', 'henry@email.com', 'pass123', N'Henry Vũ', NULL, NULL, GETDATE(), 1, 0),
('ivy', 'ivy@email.com', 'pass123', N'Ivy Hồ', NULL, N'Tôi là Ivy', GETDATE(), 1, 1),
('jack', 'jack@email.com', 'pass123', N'Jack Nguyễn', NULL, NULL, GETDATE(), 1, 1);

-- Insert Posts
INSERT INTO Posts (user_id, content, image_url, video_url, created_at, is_deleted) VALUES
(1, N'Bài đầu tiên của mình!', 'img1.jpg', NULL, GETDATE(), 0),
(2, N'Trời hôm nay thật đẹp', 'img2.jpg', NULL, GETDATE(), 0),
(3, N'Check-in Đà Lạt', 'img3.jpg', NULL, GETDATE(), 0),
(4, N'Góc học tập của mình', 'img4.jpg', NULL, GETDATE(), 0),
(5, N'Tôi yêu sách', 'img5.jpg', NULL, GETDATE(), 0),
(6, N'Đi biển vui quá', 'img6.jpg', NULL, GETDATE(), 0),
(7, N'Chill cùng bạn bè', 'img7.jpg', NULL, GETDATE(), 0),
(8, N'Hôm nay ăn gì nhỉ?', 'img8.jpg', NULL, GETDATE(), 0),
(9, N'Bức ảnh kỷ niệm', 'img9.jpg', NULL, GETDATE(), 0),
(10, N'Happy weekend!', 'img10.jpg', NULL, GETDATE(), 0);

-- Insert Comments
INSERT INTO Comments (post_id, user_id, content, created_at) VALUES
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

-- Insert Likes
INSERT INTO Likes (post_id, user_id, created_at) VALUES
(1, 2, GETDATE()), (1, 3, GETDATE()), (2, 1, GETDATE()), (2, 4, GETDATE()), (3, 5, GETDATE()),
(4, 6, GETDATE()), (5, 7, GETDATE()), (6, 8, GETDATE()), (7, 9, GETDATE()), (8, 10, GETDATE());

-- Insert Follows
INSERT INTO Follows (follower_id, following_id, created_at) VALUES
(1, 2, GETDATE()), (2, 3, GETDATE()), (3, 4, GETDATE()), (4, 5, GETDATE()), (5, 6, GETDATE()),
(6, 7, GETDATE()), (7, 8, GETDATE()), (8, 9, GETDATE()), (9, 10, GETDATE()), (10, 1, GETDATE());

-- Insert Saved_Posts
INSERT INTO Saved_Posts (user_id, post_id, created_at) VALUES
(1, 2, GETDATE()), (2, 3, GETDATE()), (3, 4, GETDATE()), (4, 5, GETDATE()), (5, 6, GETDATE()),
(6, 7, GETDATE()), (7, 8, GETDATE()), (8, 9, GETDATE()), (9, 10, GETDATE()), (10, 1, GETDATE());

-- Insert Messages
INSERT INTO Messages (sender_id, receiver_id, content, created_at, is_read) VALUES
(1, 2, N'Hello Bob!', GETDATE(), 1),
(2, 1, N'Hi Alice!', GETDATE(), 1),
(3, 4, N'Chào bạn', GETDATE(), 0),
(4, 5, N'Bạn rảnh không?', GETDATE(), 0),
(5, 6, N'Đi ăn không?', GETDATE(), 0),
(6, 7, N'Có vé xem phim không?', GETDATE(), 0),
(7, 8, N'Làm bài chưa?', GETDATE(), 0),
(8, 9, N'Chúc ngủ ngon', GETDATE(), 0),
(9, 10, N'Rảnh cafe không?', GETDATE(), 0),
(10, 1, N'Sáng mai đi học nha', GETDATE(), 0);

-- Insert Devices
INSERT INTO Devices (user_id, device_token, device_name, last_login, is_active) VALUES
(1, 'token1', N'iPhone 13', GETDATE(), 1),
(2, 'token2', N'Galaxy S21', GETDATE(), 1),
(3, 'token3', N'Macbook Pro', GETDATE(), 1),
(4, 'token4', N'Laptop HP', GETDATE(), 1),
(5, 'token5', N'iPad Air', GETDATE(), 1),
(6, 'token6', N'Xiaomi Note', GETDATE(), 1),
(7, 'token7', N'Dell XPS', GETDATE(), 1),
(8, 'token8', N'Samsung Tab', GETDATE(), 1),
(9, 'token9', N'Asus Zenbook', GETDATE(), 1),
(10, 'token10', N'Lenovo Yoga', GETDATE(), 1);

-- Insert Notifications
INSERT INTO Notifications (user_id, type, message, link, is_read, created_at) VALUES
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

-- Insert User_Behaviors
INSERT INTO User_Behaviors (user_id, action, target_id, created_at) VALUES
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

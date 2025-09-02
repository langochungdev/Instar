
CREATE TABLE roles (
                       id INT IDENTITY(1,1) PRIMARY KEY,
                       name NVARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE app_users (
                           id UNIQUEIDENTIFIER PRIMARY KEY,
                           username VARCHAR(50) NOT NULL UNIQUE,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           password VARCHAR(255) NOT NULL,
                           full_name NVARCHAR(100) NOT NULL,
                           avatar_url VARCHAR(255),
                           bio NVARCHAR(255),
                           created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
                           last_login DATETIME2,
                           is_active BIT NOT NULL,
                           is_verified BIT NOT NULL
);

CREATE TABLE user_roles (
                            id UNIQUEIDENTIFIER PRIMARY KEY,
                            user_id UNIQUEIDENTIFIER NOT NULL,
                            role_id INT NOT NULL,
                            CONSTRAINT fk_userroles_user FOREIGN KEY (user_id) REFERENCES app_users(id),
                            CONSTRAINT fk_userroles_role FOREIGN KEY (role_id) REFERENCES roles(id),
                            CONSTRAINT uq_userroles UNIQUE (user_id, role_id)
);

CREATE TABLE chats (
                       id UNIQUEIDENTIFIER PRIMARY KEY,
                       chat_name NVARCHAR(100) NULL,
                       is_group BIT NOT NULL DEFAULT 0,
                       created_by UNIQUEIDENTIFIER NOT NULL,
                       created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
                       CONSTRAINT fk_chats_user FOREIGN KEY (created_by) REFERENCES app_users(id)
);

CREATE TABLE chat_users (
                            id UNIQUEIDENTIFIER PRIMARY KEY,
                            chat_id UNIQUEIDENTIFIER NOT NULL,
                            user_id UNIQUEIDENTIFIER NOT NULL,
                            is_admin BIT NOT NULL,
                            CONSTRAINT fk_chatusers_chat FOREIGN KEY (chat_id) REFERENCES chats(id),
                            CONSTRAINT fk_chatusers_user FOREIGN KEY (user_id) REFERENCES app_users(id),
                            CONSTRAINT uq_chatusers UNIQUE (chat_id, user_id)
);

CREATE TABLE messages (
                          id UNIQUEIDENTIFIER PRIMARY KEY,
                          chat_id UNIQUEIDENTIFIER NOT NULL,
                          sender_id UNIQUEIDENTIFIER NOT NULL,
                          content NVARCHAR(500) NULL,
                          created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
                          is_read BIT NOT NULL DEFAULT 0,
                          FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE,
                          FOREIGN KEY (sender_id) REFERENCES app_users(id)
);

CREATE TABLE message_attachments (
                                     id UNIQUEIDENTIFIER PRIMARY KEY,
                                     message_id UNIQUEIDENTIFIER NOT NULL,
                                     file_url NVARCHAR(255) NOT NULL,
                                     file_type NVARCHAR(50) NOT NULL,
                                     FOREIGN KEY (message_id) REFERENCES messages(id) ON DELETE CASCADE
);

CREATE TABLE posts (
                       id UNIQUEIDENTIFIER PRIMARY KEY,
                       user_id UNIQUEIDENTIFIER NOT NULL,
                       content NVARCHAR(500) NULL,
                       created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
                       updated_at DATETIME2 NULL,
                       is_deleted BIT NOT NULL DEFAULT 0,
                       FOREIGN KEY (user_id) REFERENCES app_users(id)
);

CREATE TABLE post_media (
                            id UNIQUEIDENTIFIER PRIMARY KEY,
                            post_id UNIQUEIDENTIFIER NOT NULL,
                            media_url NVARCHAR(255) NOT NULL,
                            media_type NVARCHAR(50) NOT NULL,
                            FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

CREATE TABLE comments (
                          id UNIQUEIDENTIFIER PRIMARY KEY,
                          post_id UNIQUEIDENTIFIER NOT NULL,
                          user_id UNIQUEIDENTIFIER NOT NULL,
                          content NVARCHAR(255) NOT NULL,
                          created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
                          parent_id UNIQUEIDENTIFIER NULL,
                          FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES app_users(id),
);

CREATE TABLE likes (
                       id UNIQUEIDENTIFIER PRIMARY KEY,
                       user_id UNIQUEIDENTIFIER NOT NULL,
                       target_id UNIQUEIDENTIFIER NOT NULL,
                       target_type NVARCHAR(20) NOT NULL CHECK (target_type IN ('POST', 'COMMENT')), -- phân biệt loại
                       created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
                       FOREIGN KEY (user_id) REFERENCES app_users(id),
                       CONSTRAINT uq_likes UNIQUE (user_id, target_id, target_type)
);


CREATE TABLE follows (
                         id UNIQUEIDENTIFIER PRIMARY KEY,
                         follower_id UNIQUEIDENTIFIER NOT NULL,
                         following_id UNIQUEIDENTIFIER NOT NULL,
                         created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
                         FOREIGN KEY (follower_id) REFERENCES app_users(id),
                         FOREIGN KEY (following_id) REFERENCES app_users(id),
                         CONSTRAINT uq_follows UNIQUE (follower_id, following_id),
                         CONSTRAINT ck_follows_no_self CHECK (follower_id <> following_id)
);

CREATE TABLE saved_posts (
                             id UNIQUEIDENTIFIER PRIMARY KEY,
                             user_id UNIQUEIDENTIFIER NOT NULL,
                             post_id UNIQUEIDENTIFIER NOT NULL,
                             created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
                             FOREIGN KEY (user_id) REFERENCES app_users(id),
                             FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
                             CONSTRAINT uq_saved_posts UNIQUE (user_id, post_id)
);

CREATE TABLE devices (
                         id UNIQUEIDENTIFIER PRIMARY KEY,
                         user_id UNIQUEIDENTIFIER NOT NULL,
                         device_id VARCHAR(255) NOT NULL,
                         device_name NVARCHAR(100) NOT NULL,
                         last_login DATETIME2 NOT NULL,
                         is_active BIT NOT NULL,
                         push_token VARCHAR(255),
                         CONSTRAINT fk_devices_user FOREIGN KEY (user_id) REFERENCES app_users(id),
                         CONSTRAINT uq_devices UNIQUE (user_id, device_id)
);

CREATE TABLE notifications (
                               id UNIQUEIDENTIFIER PRIMARY KEY,
                               user_id UNIQUEIDENTIFIER NOT NULL,
                               type NVARCHAR(50) NOT NULL,
                               message NVARCHAR(255) NOT NULL,
                               link NVARCHAR(255) NULL,
                               target_id UNIQUEIDENTIFIER NULL,
                               target_type NVARCHAR(20) NULL CHECK (target_type IN ('POST','COMMENT','USER','CHAT')),

                               is_read BIT NOT NULL DEFAULT 0,
                               created_at DATETIME2 NOT NULL DEFAULT GETDATE(),

                               FOREIGN KEY (user_id) REFERENCES app_users(id)
);


CREATE TABLE user_behaviors (
                                id UNIQUEIDENTIFIER PRIMARY KEY,
                                user_id UNIQUEIDENTIFIER NOT NULL,
                                action NVARCHAR(50) NOT NULL,
                                target_id UNIQUEIDENTIFIER NOT NULL,
                                target_type NVARCHAR(20) NOT NULL CHECK (target_type IN ('POST','COMMENT','USER','CHAT')),
                                created_at DATETIME2 NOT NULL DEFAULT GETDATE(),
                                FOREIGN KEY (user_id) REFERENCES app_users(id)
);


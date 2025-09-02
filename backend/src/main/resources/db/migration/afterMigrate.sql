-- afterMigrate.sql

-- ROLES
IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN')
    INSERT INTO roles (name) VALUES ('ADMIN');

IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'USER')
    INSERT INTO roles (name) VALUES ('USER');

-- USERS
IF NOT EXISTS (SELECT 1 FROM app_users WHERE id = '11111111-1111-1111-1111-111111111111')
    INSERT INTO app_users (id, username, email, password, full_name, avatar_url, bio, created_at, is_active, is_verified)
    VALUES ('11111111-1111-1111-1111-111111111111', 'admin', 'admin@email.com',
            '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS',
            N'La Hung Admin', NULL, N'Chào mọi người!', GETDATE(), 1, 1);

IF NOT EXISTS (SELECT 1 FROM app_users WHERE id = '22222222-2222-2222-2222-222222222222')
    INSERT INTO app_users (id, username, email, password, full_name, avatar_url, bio, created_at, is_active, is_verified)
    VALUES ('22222222-2222-2222-2222-222222222222', 'user', 'user@email.com',
            '$2a$12$.NALdJeDlmXUugMWI2AniO5CbhgsPWm9gDkWxZPA4nj/t118ieHRS',
            N'La Hung User', NULL, N'Yêu du lịch', GETDATE(), 1, 1);

-- USER_ROLES (lấy role_id theo name để tránh sai id tự tăng)
DECLARE @role_admin INT = (SELECT id FROM roles WHERE name = 'ADMIN');
DECLARE @role_user INT  = (SELECT id FROM roles WHERE name = 'USER');

IF NOT EXISTS (SELECT 1 FROM user_roles WHERE id = 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa')
    INSERT INTO user_roles (id, user_id, role_id)
    VALUES ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '11111111-1111-1111-1111-111111111111', @role_admin);

IF NOT EXISTS (SELECT 1 FROM user_roles WHERE id = 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb')
    INSERT INTO user_roles (id, user_id, role_id)
    VALUES ('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '11111111-1111-1111-1111-111111111111', @role_user);

IF NOT EXISTS (SELECT 1 FROM user_roles WHERE id = 'cccccccc-cccc-cccc-cccc-cccccccccccc')
    INSERT INTO user_roles (id, user_id, role_id)
    VALUES ('cccccccc-cccc-cccc-cccc-cccccccccccc', '22222222-2222-2222-2222-222222222222', @role_user);

-- -- CHATS
-- IF NOT EXISTS (SELECT 1 FROM chats WHERE id = '33333333-3333-3333-3333-333333333333')
--     INSERT INTO chats (id, chat_name, is_group, created_by, created_at)
--     VALUES ('33333333-3333-3333-3333-333333333333', NULL, 1, '11111111-1111-1111-1111-111111111111', GETDATE());
--
-- -- CHAT_USERS
-- IF NOT EXISTS (SELECT 1 FROM chat_users WHERE id = '77777777-7777-7777-7777-777777777777')
--     INSERT INTO chat_users (id, chat_id, user_id, is_admin)
--     VALUES ('77777777-7777-7777-7777-777777777777', '33333333-3333-3333-3333-333333333333',
--             '11111111-1111-1111-1111-111111111111', 1);
--
-- IF NOT EXISTS (SELECT 1 FROM chat_users WHERE id = '88888888-8888-8888-8888-888888888888')
--     INSERT INTO chat_users (id, chat_id, user_id, is_admin)
--     VALUES ('88888888-8888-8888-8888-888888888888', '33333333-3333-3333-3333-333333333333',
--             '22222222-2222-2222-2222-222222222222', 0);
--


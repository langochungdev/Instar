### User APIs
POST http://localhost:8080/api/users/register
POST http://localhost:8080/api/users/login
GET http://localhost:8080/api/users/{id}
PUT http://localhost:8080/api/users/{id}
PUT http://localhost:8080/api/users/{id}/password
POST http://localhost:8080/api/users/{id}/verify
GET http://localhost:8080/api/devices
POST http://localhost:8080/api/devices
DELETE http://localhost:8080/api/devices/{id}
GET http://localhost:8080/api/users/{userId}/devices
POST http://localhost:8080/api/users/{userId}/devices
DELETE http://localhost:8080/api/users/{userId}/devices/{deviceId}

### Post APIs
POST http://localhost:8080/api/posts
GET http://localhost:8080/api/posts/{id}
PUT http://localhost:8080/api/posts/{id}
DELETE http://localhost:8080/api/posts/{id}
GET http://localhost:8080/api/posts
GET http://localhost:8080/api/users/{userId}/posts

### Comment APIs
POST http://localhost:8080/api/posts/{postId}/comments
GET http://localhost:8080/api/posts/{postId}/comments
POST http://localhost:8080/api/comments/{commentId}/reply
PUT http://localhost:8080/api/comments/{id}
DELETE http://localhost:8080/api/comments/{id}

### Like, Follow, Save APIs
POST http://localhost:8080/api/posts/{postId}/like
DELETE http://localhost:8080/api/posts/{postId}/like
GET http://localhost:8080/api/posts/{postId}/like
POST http://localhost:8080/api/users/{userId}/follow
DELETE http://localhost:8080/api/users/{userId}/follow
GET http://localhost:8080/api/users/{userId}/follow
POST http://localhost:8080/api/posts/{postId}/save
DELETE http://localhost:8080/api/posts/{postId}/save
GET http://localhost:8080/api/posts/{postId}/save

### Message & Notification APIs
POST http://localhost:8080/api/messages
GET http://localhost:8080/api/messages/conversations
PUT http://localhost:8080/api/messages/{id}/read
GET http://localhost:8080/api/users/{userId}/notifications
PUT http://localhost:8080/api/notifications/{id}/read

### User Behavior APIs
POST http://localhost:8080/api/user-behaviors
GET http://localhost:8080/api/users/{userId}/behaviors

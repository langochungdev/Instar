//package com.instar.feature.chat.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.io.Serializable;
//
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class ChatUserId implements Serializable {
//    private Integer chat;
//    private Integer user;
//}

package com.instar.feature.chat.entity;

import java.io.Serializable;
import java.util.Objects;

public class ChatUserId implements Serializable {
    private Integer chat;
    private Integer user;

    public ChatUserId() {}

    public ChatUserId(Integer chat, Integer user) {
        this.chat = chat;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatUserId that)) return false;
        return Objects.equals(chat, that.chat) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chat, user);
    }
}


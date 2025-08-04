package com.instar.feature.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatUserId implements Serializable {
    private Integer chat;
    private Integer user;
}

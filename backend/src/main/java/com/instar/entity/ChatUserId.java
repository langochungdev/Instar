package com.instar.entity;

import lombok.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatUserId implements Serializable {
    private Integer chat;
    private Integer user;
}

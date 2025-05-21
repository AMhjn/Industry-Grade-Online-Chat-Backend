package com.abhir.chat.user.dto;

import lombok.Data;

@Data
public class UserResponse {
    private String username;
    private String displayName;
    private String bio;
    private String avatarUrl;
}

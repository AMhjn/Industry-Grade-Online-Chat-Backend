package com.abhir.chat.user.dto;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String displayName;
    private String bio;
    private String avatarUrl; // placeholder for now
}

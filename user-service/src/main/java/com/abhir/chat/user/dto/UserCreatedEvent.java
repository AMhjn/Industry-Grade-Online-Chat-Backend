package com.abhir.chat.user.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedEvent {
    private String username;
    private String displayName;
}

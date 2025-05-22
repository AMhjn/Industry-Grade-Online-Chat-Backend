package com.abhir.chat.message.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private String sender;
    private String recipient;
    private String content;
    private Instant timestamp;
}

package com.abhir.chat.message.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSentEvent {
    private String messageId;
    private String sender;
    private String receiver;
    private String content;
    private Instant timestamp;
}

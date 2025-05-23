package com.abhir.chat.message.controller;

import com.abhir.chat.message.dto.MessageResponse;
import com.abhir.chat.message.dto.SendMessageRequest;
import com.abhir.chat.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MessageWebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/chat/{recipient}")
    public void sendMessage(@DestinationVariable String recipient, @Payload SendMessageRequest request) {
        String sender = SecurityContextHolder.getContext().getAuthentication().getName();

        request.setRecipient(recipient); // Ensure correct routing
        messageService.sendMessage(request); // Persist + Kafka

        MessageResponse response = MessageResponse.builder()
                .sender(sender)
                .recipient(recipient)
                .content(request.getContent())
                .timestamp(java.time.Instant.now())
                .build();

        // Deliver message to recipient if they're subscribed
        messagingTemplate.convertAndSend("/topic/messages/" + recipient, response);
    }
}

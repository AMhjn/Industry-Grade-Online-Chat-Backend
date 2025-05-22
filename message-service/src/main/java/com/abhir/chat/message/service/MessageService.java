package com.abhir.chat.message.service;

import com.abhir.chat.message.dto.MessageResponse;
import com.abhir.chat.message.dto.MessageSentEvent;
import com.abhir.chat.message.dto.SendMessageRequest;
import com.abhir.chat.message.entity.Message;
import com.abhir.chat.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final KafkaTemplate<String, MessageSentEvent> kafkaTemplate;

    public void sendMessage(SendMessageRequest request) {
        String sender = SecurityContextHolder.getContext().getAuthentication().getName();

        Message savedMessage = Message.builder()
                .sender(sender)
                .recipient(request.getRecipient())
                .content(request.getContent())
                .timestamp(Instant.now())
                .build();

        messageRepository.save(savedMessage);

        // 🔥 Emit Kafka Event
        MessageSentEvent event = new MessageSentEvent(
                savedMessage.getId().toString(),
                savedMessage.getSender(),
                savedMessage.getRecipient(),
                savedMessage.getContent(),
                savedMessage.getTimestamp()
        );

        kafkaTemplate.send("message.sent", event);
    }


    public List<MessageResponse> getMessagesWith(String user) {
        String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();

        return messageRepository.findBySenderOrRecipient(currentUser, user).stream()
                .filter(m -> m.getSender().equals(user) || m.getRecipient().equals(user))
                .map(m -> MessageResponse.builder()
                        .sender(m.getSender())
                        .recipient(m.getRecipient())
                        .content(m.getContent())
                        .timestamp(m.getTimestamp())
                        .build())
                .collect(Collectors.toList());
    }
}

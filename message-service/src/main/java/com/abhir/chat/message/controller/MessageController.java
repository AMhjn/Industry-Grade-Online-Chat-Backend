package com.abhir.chat.message.controller;

import com.abhir.chat.message.dto.SendMessageRequest;
import com.abhir.chat.message.dto.MessageResponse;
import com.abhir.chat.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Void> sendMessage(@RequestBody SendMessageRequest request) {
        messageService.sendMessage(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{user}")
    public ResponseEntity<List<MessageResponse>> getMessagesWith(@PathVariable String user) {
        return ResponseEntity.ok(messageService.getMessagesWith(user));
    }
}

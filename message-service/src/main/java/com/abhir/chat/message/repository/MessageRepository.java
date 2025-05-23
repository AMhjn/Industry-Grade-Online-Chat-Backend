package com.abhir.chat.message.repository;

import com.abhir.chat.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderOrRecipient(String sender, String recipient);
}

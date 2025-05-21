package com.abhir.chat.user.service;

import com.abhir.chat.user.dto.UserCreatedEvent;
import com.abhir.chat.user.entity.User;
import com.abhir.chat.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserCreatedConsumer {

    private final UserRepository userRepository;

    @PostConstruct
    public void checkInit() {
        System.out.println("🔥 UserCreatedConsumer is initialized");
        System.out.println("UserCreatedConsumer is initialized");
    }

    @KafkaListener(topics = "user.created", groupId = "user-profile-consumers",containerFactory = "userCreatedKafkaListenerContainerFactory")
    public void handleUserCreated(UserCreatedEvent event) {
        System.out.println("Received user.created event: {}" + event);

        if (userRepository.findByUsername(event.getUsername()).isEmpty()) {
            User user = User.builder()
                    .username(event.getUsername())
                    .displayName(event.getDisplayName())
                    .build();
            userRepository.save(user);
            System.out.println("User profile created for: {}"+ event.getUsername());
        } else {
            System.out.println("User already exists: {}"+ event.getUsername());
        }
    }
}

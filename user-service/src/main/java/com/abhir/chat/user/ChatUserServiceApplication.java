package com.abhir.chat.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class ChatUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatUserServiceApplication.class, args);
    }
}

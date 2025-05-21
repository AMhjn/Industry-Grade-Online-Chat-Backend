package com.abhir.chat.auth.service;

import com.abhir.chat.auth.dto.AuthRequest;
import com.abhir.chat.auth.dto.AuthResponse;
import com.abhir.chat.auth.dto.SignupRequest;
import com.abhir.chat.auth.dto.UserCreatedEvent;
import com.abhir.chat.auth.entity.User;
import com.abhir.chat.auth.repository.UserRepository;
import com.abhir.chat.auth.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    public void register(SignupRequest request) {
        log.info("📩 Received signup request: {}", request);

        try {
            User user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .displayName(request.getDisplayName())
                    .build();
            userRepository.save(user);

            System.out.println("Started  CREATing KAFKA EVENT ------------------------>>>>>>>>>>>>>>>>>");

            UserCreatedEvent event = new UserCreatedEvent(user.getUsername(), user.getDisplayName());
            kafkaTemplate.send("user.created", event);

            System.out.println("SUCCESSFULLY CREATED KAFKA EVENT ------------------------>>>>>>>>>>>>>>>>>");

        } catch (Exception e) {
            System.out.println("❌ Error in register():" +  e);
        }
    }


    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}


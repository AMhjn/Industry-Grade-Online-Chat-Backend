package com.abhir.chat.user.service;

import com.abhir.chat.user.dto.UserResponse;
import com.abhir.chat.user.dto.UserUpdateRequest;
import com.abhir.chat.user.entity.User;
import com.abhir.chat.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getCurrentUserProfile() {
        String username = getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setDisplayName(user.getDisplayName());
        response.setBio(user.getBio());
        response.setAvatarUrl(user.getAvatarUrl());
        return response;
    }

    public void updateCurrentUserProfile(UserUpdateRequest request) {
        String username = getAuthenticatedUsername();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setDisplayName(request.getDisplayName());
        user.setBio(request.getBio());
        user.setAvatarUrl(request.getAvatarUrl());

        userRepository.save(user);
    }

    private String getAuthenticatedUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}

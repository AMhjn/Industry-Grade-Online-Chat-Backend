package com.abhir.chat.user.controller;

import com.abhir.chat.user.dto.UserResponse;
import com.abhir.chat.user.dto.UserUpdateRequest;
import com.abhir.chat.user.service.FileUploadService;
import com.abhir.chat.user.service.PresenceService;
import com.abhir.chat.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PresenceService presenceService;
    private final FileUploadService fileUploadService;



    @GetMapping("/me")
    public ResponseEntity<UserResponse> getMyProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    @PatchMapping("/me")
    public ResponseEntity<Void> updateMyProfile(@RequestBody UserUpdateRequest request) {
        userService.updateCurrentUserProfile(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{username}")
    public ResponseEntity<String> getUserStatus(@PathVariable String username) {
        boolean isOnline = presenceService.isOnline(username);
        return ResponseEntity.ok(isOnline ? "online" : "offline");
    }

    @PostMapping("/me/avatar")
    public ResponseEntity<String> uploadAvatar(@RequestParam String fileName) {
        String url = fileUploadService.generateMockPresignedUrl(fileName);
        return ResponseEntity.ok(url);
    }

}

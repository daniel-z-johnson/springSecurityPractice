package com.practice.security.controller;

import com.practice.security.dto.ApiResponse;
import com.practice.security.dto.ProfileResponse;
import com.practice.security.dto.ProfileUpdateRequest;
import com.practice.security.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    
    @GetMapping
    public ResponseEntity<ApiResponse> getProfile() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            ProfileResponse profile = profileService.getProfile(username);
            return ResponseEntity.ok(ApiResponse.success("Profile retrieved successfully", profile));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
    
    @PutMapping
    public ResponseEntity<ApiResponse> updateProfile(@RequestBody ProfileUpdateRequest request) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            
            ProfileResponse profile = profileService.updateProfile(username, request);
            return ResponseEntity.ok(ApiResponse.success("Profile updated successfully", profile));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error(e.getMessage()));
        }
    }
}

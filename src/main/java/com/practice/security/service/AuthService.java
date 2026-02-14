package com.practice.security.service;

import com.practice.security.dto.SignupRequest;
import com.practice.security.mapper.UserMapper;
import com.practice.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    
    @Transactional
    public void signup(SignupRequest request) {
        if (userMapper.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .build();
        
        userMapper.insertUser(user);
        profileService.createProfile(user.getId());
    }
}

package com.practice.security.service;

import com.practice.security.dto.ProfileResponse;
import com.practice.security.dto.ProfileUpdateRequest;
import com.practice.security.exception.ResourceNotFoundException;
import com.practice.security.mapper.ProfileMapper;
import com.practice.security.mapper.UserMapper;
import com.practice.security.model.Profile;
import com.practice.security.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final ProfileMapper profileMapper;
    private final UserMapper userMapper;
    
    @Transactional
    public void createProfile(Long userId) {
        Profile profile = Profile.builder()
                .userId(userId)
                .favoriteTechnologies(new ArrayList<>())
                .favoriteVideoGames(new ArrayList<>())
                .favoriteBooks(new ArrayList<>())
                .build();
        profileMapper.insertProfile(profile);
    }
    
    @Transactional(readOnly = true)
    public ProfileResponse getProfile(String username) {
        User user = userMapper.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        Profile profile = profileMapper.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
        
        return ProfileResponse.builder()
                .username(user.getUsername())
                .favoriteTechnologies(profile.getFavoriteTechnologies())
                .favoriteVideoGames(profile.getFavoriteVideoGames())
                .favoriteBooks(profile.getFavoriteBooks())
                .build();
    }
    
    @Transactional
    public ProfileResponse updateProfile(String username, ProfileUpdateRequest request) {
        User user = userMapper.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        
        Profile profile = profileMapper.findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
        
        if (request.getFavoriteTechnologies() != null) {
            profile.setFavoriteTechnologies(request.getFavoriteTechnologies());
        }
        if (request.getFavoriteVideoGames() != null) {
            profile.setFavoriteVideoGames(request.getFavoriteVideoGames());
        }
        if (request.getFavoriteBooks() != null) {
            profile.setFavoriteBooks(request.getFavoriteBooks());
        }
        
        profileMapper.updateProfile(profile);
        
        return ProfileResponse.builder()
                .username(user.getUsername())
                .favoriteTechnologies(profile.getFavoriteTechnologies())
                .favoriteVideoGames(profile.getFavoriteVideoGames())
                .favoriteBooks(profile.getFavoriteBooks())
                .build();
    }
}

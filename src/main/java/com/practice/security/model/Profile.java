package com.practice.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    private Long id;
    private Long userId;
    private List<String> favoriteTechnologies;
    private List<String> favoriteVideoGames;
    private List<String> favoriteBooks;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

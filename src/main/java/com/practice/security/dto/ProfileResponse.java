package com.practice.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private String username;
    private List<String> favoriteTechnologies;
    private List<String> favoriteVideoGames;
    private List<String> favoriteBooks;
}

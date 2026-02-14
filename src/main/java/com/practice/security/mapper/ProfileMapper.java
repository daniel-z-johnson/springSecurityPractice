package com.practice.security.mapper;

import com.practice.security.model.Profile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface ProfileMapper {
    void insertProfile(Profile profile);
    
    Optional<Profile> findByUserId(@Param("userId") Long userId);
    
    void updateProfile(Profile profile);
}

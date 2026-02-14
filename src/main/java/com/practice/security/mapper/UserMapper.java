package com.practice.security.mapper;

import com.practice.security.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {
    void insertUser(User user);
    
    Optional<User> findByUsername(@Param("username") String username);
    
    Optional<User> findById(@Param("id") Long id);
    
    void updateUser(User user);
}

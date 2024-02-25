package com.jwtauthentication.jwt.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jwtauthentication.jwt.dto.UserDto;
import com.jwtauthentication.jwt.dto.UserResponseDto;

public interface UserService extends UserDetailsService {
    void addNewUser(UserDto userDto);

    List<UserResponseDto> getAllUsers();

}

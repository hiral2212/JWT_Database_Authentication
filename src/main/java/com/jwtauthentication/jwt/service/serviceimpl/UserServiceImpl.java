package com.jwtauthentication.jwt.service.serviceimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import com.jwtauthentication.jwt.dto.UserDto;
import com.jwtauthentication.jwt.dto.UserResponseDto;
import com.jwtauthentication.jwt.exception.EntityNotFoundException;
import com.jwtauthentication.jwt.model.Role;
import com.jwtauthentication.jwt.model.User;
import com.jwtauthentication.jwt.repository.UserRepository;
import com.jwtauthentication.jwt.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        User user = userRepository.findByUsername(username)
        .orElseThrow(()->new EntityNotFoundException("user not found")); 
return user;
    }

    @Override
    public void addNewUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getUsername());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, "ROLE_USER"));
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        // TODO Auto-generated method stub
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserResponseDto.class)).toList();
    }

}

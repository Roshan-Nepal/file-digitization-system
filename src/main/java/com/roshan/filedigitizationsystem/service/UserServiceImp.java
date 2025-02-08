package com.roshan.filedigitizationsystem.service;

import com.roshan.filedigitizationsystem.dto.request.UserRequestDto;
import com.roshan.filedigitizationsystem.dto.request.UserLoginDto;
import com.roshan.filedigitizationsystem.dto.request.UserUpdateDto;
import com.roshan.filedigitizationsystem.dto.response.UserResponseDto;
import com.roshan.filedigitizationsystem.entity.UserInfo;
import com.roshan.filedigitizationsystem.enums.UserRole;
import com.roshan.filedigitizationsystem.exception.InvalidFieldException;
import com.roshan.filedigitizationsystem.exception.NoSuchUserExistsException;
import com.roshan.filedigitizationsystem.exception.UserAlreadyExistsException;
import com.roshan.filedigitizationsystem.mapper.UserMapper;
import com.roshan.filedigitizationsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Set;

@Service
public class UserServiceImp implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final UserRepo userRepo;

    @Autowired
    public UserServiceImp(UserRepo userRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void addUserInfo(UserRequestDto userRequestDto) {
        UserInfo userInfo = UserInfo.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder
                        .encode(userRequestDto.getPassword()
                        ))
                .roles(Set.of(UserRole.ROLE_USER))
                .build();
        if (userRepo.findByUsername(userInfo.getUsername()) != null) {
            throw new UserAlreadyExistsException("User already exists with username: " + userInfo.getUsername());
        }
        if (userRepo.findByEmail(userInfo.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists with email: " + userInfo.getEmail());
        }
        userRepo.save(userInfo);

    }

    @Override
    public UserResponseDto login(UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            return UserMapper.toUserDto((UserInfo) authentication.getPrincipal());
        }
        throw new BadCredentialsException("Invalid credentials");
    }

    @Override
    public void updateUserInfo(Long userId, UserUpdateDto<String> userUpdateDto) {
        UserInfo userInfo = userRepo.findById(userId).orElseThrow(NoSuchUserExistsException::new);

        switch (userUpdateDto.getAction()) {
            case "username":
                userInfo.setUsername(userUpdateDto.getT());
                break;
            case "email":
                userInfo.setEmail(userUpdateDto.getT());
                break;
            case "password":
                userInfo.setPassword(passwordEncoder.encode(userUpdateDto.getT()));
                break;
            default:
                throw new InvalidFieldException("Invalid Field");
        }
        userRepo.save(userInfo);
    }

    @Override
    public void updateUserRole(Long userId, UserUpdateDto<UserRole> userUpdateDto) {
        UserInfo userInfo = userRepo.findById(userId).orElseThrow(NoSuchUserExistsException::new);
        switch (userUpdateDto.getAction()) {
            case "add":
                userInfo.getRoles().add(userUpdateDto.getT());
                break;
            case "remove":
                userInfo.getRoles().remove(userUpdateDto.getT());
                break;
            default:
                throw new InvalidFieldException("Invalid Field");
        }
        userRepo.save(userInfo);

    }

    @Override
    public void deleteUser(Long userId) {
        UserInfo userInfo = userRepo.findById(userId).orElseThrow(NoSuchUserExistsException::new);
        userRepo.delete(userInfo);
        if (userRepo.findById(userId).isPresent()) {
            throw new InternalError("User deletion failed");
        }
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return userRepo.findAll().stream().map(UserMapper::toUserDto).toList();
    }

    @Override
    public UserResponseDto getUser(Long userId) {
        UserInfo userInfo = userRepo.findById(userId).orElseThrow(NoSuchUserExistsException::new);
        return UserMapper.toUserDto(userInfo);
    }

}

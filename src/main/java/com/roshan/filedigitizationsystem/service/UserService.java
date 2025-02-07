package com.roshan.filedigitizationsystem.service;

import com.roshan.filedigitizationsystem.dto.request.UserRequestDto;
import com.roshan.filedigitizationsystem.dto.request.UserLoginDto;
import com.roshan.filedigitizationsystem.dto.request.UserUpdateDto;
import com.roshan.filedigitizationsystem.dto.response.UserResponseDto;
import com.roshan.filedigitizationsystem.enums.UserRole;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    UserResponseDto login(UserLoginDto userLoginDto);

    void addUserInfo(UserRequestDto userRequestDto);

    void updateUserInfo(Long userId, UserUpdateDto<String> userUpdateDto);

    void updateUserRole(Long userId, UserUpdateDto<UserRole> userUpdateDto);

    void deleteUser(Long userId);

    List<UserResponseDto> getAllUsers();

    UserResponseDto getUser(Long userId);
}

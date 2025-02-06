package com.roshan.filedigitizationsystem.service;

import com.roshan.filedigitizationsystem.dto.request.UserRequestDto;
import com.roshan.filedigitizationsystem.dto.request.UserLoginDto;
import com.roshan.filedigitizationsystem.dto.request.UserUpdateDto;
import com.roshan.filedigitizationsystem.enums.UserRole;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> login(UserLoginDto userLoginDto);
    ResponseEntity<?> addUserInfo(UserRequestDto userRequestDto);

    ResponseEntity<?> updateUserInfo(Long userId, UserUpdateDto<String> userUpdateDto);

    ResponseEntity<?> updateUserRole(Long userId, UserUpdateDto<UserRole> userUpdateDto);

    ResponseEntity<?> deleteUser(Long userId);

    ResponseEntity<?> getAllUsers();

    ResponseEntity<?> getUser(Long userId);
}

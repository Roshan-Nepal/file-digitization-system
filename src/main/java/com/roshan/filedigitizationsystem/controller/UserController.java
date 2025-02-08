package com.roshan.filedigitizationsystem.controller;

import com.roshan.filedigitizationsystem.dto.request.UserUpdateDto;
import com.roshan.filedigitizationsystem.dto.response.UserResponseDto;
import com.roshan.filedigitizationsystem.enums.UserRole;
import com.roshan.filedigitizationsystem.exception.NoSuchUserExistsException;
import com.roshan.filedigitizationsystem.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private  UserServiceImp userServiceImp;

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userServiceImp.getAllUsers());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> getUser(@PathVariable(name = "id") Long userId) {
        try {
            UserResponseDto responseDto = userServiceImp.getUser(userId);
            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        } catch (NoSuchUserExistsException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

    }


    @PutMapping("/{id}/info")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> updateUserInfo(@PathVariable(name = "id") Long userId, @RequestBody UserUpdateDto<String> userUpdateDto) {
        userServiceImp.updateUserInfo(userId, userUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body("User has been updated");
    }

    @PutMapping("/{id}/role")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> updateUserRole(@PathVariable(name = "id") Long userId, @RequestBody UserUpdateDto<UserRole> userUpdateDto) {
        userServiceImp.updateUserRole(userId, userUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body("User has been updated");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long userId) {
        userServiceImp.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body("User has been deleted");
    }


}

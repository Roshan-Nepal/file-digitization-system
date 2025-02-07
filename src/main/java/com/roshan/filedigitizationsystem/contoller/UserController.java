package com.roshan.filedigitizationsystem.contoller;

import com.roshan.filedigitizationsystem.dto.request.UserRequestDto;
import com.roshan.filedigitizationsystem.dto.request.UserLoginDto;
import com.roshan.filedigitizationsystem.dto.request.UserUpdateDto;
import com.roshan.filedigitizationsystem.dto.response.UserResponseDto;
import com.roshan.filedigitizationsystem.enums.UserRole;
import com.roshan.filedigitizationsystem.exception.NoSuchUserExistsException;
import com.roshan.filedigitizationsystem.exception.UserAlreadyExistsException;
import com.roshan.filedigitizationsystem.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    private final UserServiceImp userServiceImp;

    @Autowired
    public UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto) {
        try {
            UserResponseDto response = userServiceImp.login(userLoginDto);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }

    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> register(@RequestBody UserRequestDto userRequestDto) {
        userServiceImp.addUserInfo(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User has been registered");

    }


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

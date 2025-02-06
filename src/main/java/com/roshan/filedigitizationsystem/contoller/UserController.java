package com.roshan.filedigitizationsystem.contoller;

import com.roshan.filedigitizationsystem.dto.request.UserRequestDto;
import com.roshan.filedigitizationsystem.dto.request.UserLoginDto;
import com.roshan.filedigitizationsystem.dto.request.UserUpdateDto;
import com.roshan.filedigitizationsystem.enums.UserRole;
import com.roshan.filedigitizationsystem.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        return userServiceImp.login(userLoginDto);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> register(@RequestBody UserRequestDto userRequestDto) {
        return userServiceImp.addUserInfo(userRequestDto);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> getAllUsers() {
        return userServiceImp.getAllUsers();
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> getUser(@PathVariable(name = "id") Long userId) {
        return userServiceImp.getUser(userId);
    }


    @PutMapping("/{id}/info")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> updateUserInfo(@PathVariable(name = "id") Long userId,@RequestBody UserUpdateDto<String> userUpdateDto) {
        return userServiceImp.updateUserInfo(userId,userUpdateDto);
    }
    @PutMapping("/{id}/role")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> updateUserRole(@PathVariable(name = "id") Long userId,@RequestBody UserUpdateDto<UserRole> userUpdateDto) {
        return userServiceImp.updateUserRole(userId,userUpdateDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long userId) {
        return userServiceImp.deleteUser(userId);
    }




}

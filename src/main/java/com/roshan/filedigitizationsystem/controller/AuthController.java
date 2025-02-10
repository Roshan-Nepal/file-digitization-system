package com.roshan.filedigitizationsystem.controller;

import com.roshan.filedigitizationsystem.dto.request.RefreshTokenDto;
import com.roshan.filedigitizationsystem.dto.request.UserLoginDto;
import com.roshan.filedigitizationsystem.dto.request.UserRequestDto;
import com.roshan.filedigitizationsystem.dto.response.TokenResponseDto;
import com.roshan.filedigitizationsystem.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto userLoginDto) {
        TokenResponseDto response = authService.login(userLoginDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenDto refreshTokenDto) {
        TokenResponseDto responseDto = authService.refreshToken(refreshTokenDto.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("/register")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MODERATOR')")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequestDto userRequestDto) {
        authService.addUserInfo(userRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("User has been registered");

    }
}

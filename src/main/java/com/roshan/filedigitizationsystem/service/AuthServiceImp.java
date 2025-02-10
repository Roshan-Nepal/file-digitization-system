package com.roshan.filedigitizationsystem.service;

import com.roshan.filedigitizationsystem.dto.request.UserLoginDto;
import com.roshan.filedigitizationsystem.dto.request.UserRequestDto;
import com.roshan.filedigitizationsystem.dto.response.TokenResponseDto;
import com.roshan.filedigitizationsystem.entity.UserInfo;
import com.roshan.filedigitizationsystem.enums.UserRole;
import com.roshan.filedigitizationsystem.exception.InvalidTokenException;
import com.roshan.filedigitizationsystem.exception.UserAlreadyExistsException;
import com.roshan.filedigitizationsystem.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Slf4j
public class AuthServiceImp implements AuthService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtServiceImp jwtServiceImp;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Override
    public TokenResponseDto login(UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword())
        );
        if (authentication.isAuthenticated()) {
            String accessToken = jwtServiceImp.generateAccessToken(userLoginDto.getUsername());
            String refreshToken = jwtServiceImp.generateRefreshToken(userLoginDto.getUsername());
            log.info("{} Logged in successfully", userLoginDto.getUsername());
            return new TokenResponseDto(accessToken, refreshToken);
        }
        log.error("{} Failed to login", userLoginDto.getUsername());
        throw new BadCredentialsException("Invalid credentials");
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
            log.error("{} User already exists", userInfo.getUsername());
            throw new UserAlreadyExistsException("User already exists with username: " + userInfo.getUsername());
        }
        if (userRepo.findByEmail(userInfo.getEmail()) != null) {
            log.error("{} Email already exists", userInfo.getEmail());
            throw new UserAlreadyExistsException("User already exists with email: " + userInfo.getEmail());
        }
        log.info("Adding user {}", userInfo.getUsername());
        userRepo.save(userInfo);

    }

    @Override
    public TokenResponseDto refreshToken(String refreshToken) {
        UserInfo userInfo = userRepo.findByUsername(jwtServiceImp.extractUsername(refreshToken));
        if(jwtServiceImp.validateToken(refreshToken, userInfo)){
            String accessToken = jwtServiceImp.generateAccessToken(userInfo.getUsername());
            String refreshToken2 = jwtServiceImp.generateRefreshToken(userInfo.getUsername());
            log.info("{} Token refreshed successfully", userInfo.getUsername());
            return new TokenResponseDto(accessToken, refreshToken2);
        }
        log.error("{} Failed to refresh token", userInfo.getUsername());
        throw new InvalidTokenException();
    }
}

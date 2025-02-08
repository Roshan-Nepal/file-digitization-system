package com.roshan.filedigitizationsystem.service;

import com.roshan.filedigitizationsystem.dto.request.UserLoginDto;
import com.roshan.filedigitizationsystem.dto.request.UserRequestDto;
import com.roshan.filedigitizationsystem.dto.response.TokenResponseDto;
import com.roshan.filedigitizationsystem.entity.UserInfo;
import com.roshan.filedigitizationsystem.enums.UserRole;
import com.roshan.filedigitizationsystem.exception.UserAlreadyExistsException;
import com.roshan.filedigitizationsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
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
            return new TokenResponseDto(accessToken, refreshToken);
        }
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
            throw new UserAlreadyExistsException("User already exists with username: " + userInfo.getUsername());
        }
        if (userRepo.findByEmail(userInfo.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists with email: " + userInfo.getEmail());
        }
        userRepo.save(userInfo);

    }
}

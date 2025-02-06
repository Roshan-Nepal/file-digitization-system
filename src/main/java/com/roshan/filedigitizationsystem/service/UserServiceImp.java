package com.roshan.filedigitizationsystem.service;

import com.roshan.filedigitizationsystem.dto.request.UserRequestDto;
import com.roshan.filedigitizationsystem.dto.request.UserLoginDto;
import com.roshan.filedigitizationsystem.dto.request.UserUpdateDto;
import com.roshan.filedigitizationsystem.entity.UserInfo;
import com.roshan.filedigitizationsystem.enums.UserRole;
import com.roshan.filedigitizationsystem.exception.NoSuchUserExistsException;
import com.roshan.filedigitizationsystem.exception.UserAlreadyExistsException;
import com.roshan.filedigitizationsystem.mapper.UserMapper;
import com.roshan.filedigitizationsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


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
    public ResponseEntity<?> addUserInfo(UserRequestDto userRequestDto) {
        UserInfo userInfo = UserInfo.builder()
                .username(userRequestDto.getUsername())
                .email(userRequestDto.getEmail())
                .password(passwordEncoder
                        .encode(userRequestDto.getPassword()
                        ))
                .roles(Set.of(UserRole.ROLE_USER))
                .build();
        if(userRepo.findByUsername(userInfo.getUsername()) != null) {
            throw new UserAlreadyExistsException("User already exists with username: " + userInfo.getUsername());
        }
        if(userRepo.findByEmail(userInfo.getEmail()) != null){
            throw new UserAlreadyExistsException("User already exists with email: " + userInfo.getEmail());
        }
        userRepo.save(userInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body("User has been registered");

    }

    @Override
    public ResponseEntity<?> login(UserLoginDto userLoginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginDto.getUsername(), userLoginDto.getPassword())
        );
        if(authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.OK).body(userRepo.findByUsername(userLoginDto.getUsername()));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @Override
    public ResponseEntity<?> updateUserInfo(Long userId,UserUpdateDto<String> userUpdateDto) {
        UserInfo userInfo = userRepo.findById(userId).orElseThrow(NoSuchUserExistsException::new);

        switch (userUpdateDto.getAction()){
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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid field");

        }
        userRepo.save(userInfo);
        return ResponseEntity.status(HttpStatus.OK).body("User has been updated");
    }

    @Override
    public ResponseEntity<?> updateUserRole(Long userId, UserUpdateDto<UserRole> userUpdateDto) {
        UserInfo userInfo = userRepo.findById(userId).orElseThrow(NoSuchUserExistsException::new);
        switch (userUpdateDto.getAction()) {
            case "add":
                userInfo.getRoles().add(userUpdateDto.getT());
                break;
            case "remove":
                userInfo.getRoles().remove(userUpdateDto.getT());
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid field");
        }
        userRepo.save(userInfo);
        return ResponseEntity.status(HttpStatus.OK).body("User role has been updated.");

    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        UserInfo userInfo = userRepo.findById(userId).orElseThrow(NoSuchUserExistsException::new);
        userRepo.delete(userInfo);
        if(userRepo.findById(userId).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User deletion has been failed.");
        }
        return ResponseEntity.status(HttpStatus.OK).body("User has been deleted");
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepo.findAll().stream().map(UserMapper::toUserDto));
    }

    @Override
    public ResponseEntity<?> getUser(Long userId) {
        UserInfo userInfo = userRepo.findById(userId).orElseThrow(NoSuchUserExistsException::new);
        return ResponseEntity.status(HttpStatus.OK).body(UserMapper.toUserDto(userInfo));
    }

}

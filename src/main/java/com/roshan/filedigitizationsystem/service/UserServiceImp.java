package com.roshan.filedigitizationsystem.service;

import com.roshan.filedigitizationsystem.dto.request.UserUpdateDto;
import com.roshan.filedigitizationsystem.dto.response.UserResponseDto;
import com.roshan.filedigitizationsystem.entity.UserInfo;
import com.roshan.filedigitizationsystem.enums.UserRole;
import com.roshan.filedigitizationsystem.exception.InvalidFieldException;
import com.roshan.filedigitizationsystem.exception.NoSuchUserExistsException;
import com.roshan.filedigitizationsystem.mapper.UserMapper;
import com.roshan.filedigitizationsystem.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private  UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

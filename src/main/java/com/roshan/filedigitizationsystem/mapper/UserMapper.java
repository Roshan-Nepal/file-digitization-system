package com.roshan.filedigitizationsystem.mapper;

import com.roshan.filedigitizationsystem.dto.response.UserResponseDto;
import com.roshan.filedigitizationsystem.entity.UserInfo;

public class UserMapper {
    public static UserResponseDto toUserDto(UserInfo userInfo) {
        return UserResponseDto
                .builder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .email(userInfo.getEmail())
                .roles(userInfo.getRoles())
                .build();
    }
}

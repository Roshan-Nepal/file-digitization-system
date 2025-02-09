package com.roshan.filedigitizationsystem.service;

import com.roshan.filedigitizationsystem.dto.request.UserLoginDto;
import com.roshan.filedigitizationsystem.dto.request.UserRequestDto;
import com.roshan.filedigitizationsystem.dto.response.TokenResponseDto;

public interface AuthService {
    TokenResponseDto login(UserLoginDto userLoginDto);
    void addUserInfo(UserRequestDto userRequestDto);
    TokenResponseDto refreshToken(String refreshToken);
}

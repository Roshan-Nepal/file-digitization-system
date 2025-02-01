package com.roshan.filedigitizationsystem.service;

import com.roshan.filedigitizationsystem.dto.UserLoginDto;
import com.roshan.filedigitizationsystem.entity.UserInfo;

public interface UserService {
    UserInfo login(UserLoginDto userLoginDto);
    UserInfo addUserInfo(UserInfo userInfo);
}

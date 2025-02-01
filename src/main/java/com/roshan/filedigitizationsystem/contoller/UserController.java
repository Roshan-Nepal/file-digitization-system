package com.roshan.filedigitizationsystem.contoller;

import com.roshan.filedigitizationsystem.dto.UserLoginDto;
import com.roshan.filedigitizationsystem.entity.UserInfo;
import com.roshan.filedigitizationsystem.service.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserServiceImp userServiceImp;
    @Autowired
    public UserController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @PostMapping("/login")
    public UserInfo login(@RequestBody UserLoginDto userLoginDto) {
        return userServiceImp.login(userLoginDto);
    }

    @PostMapping("/register")
    public UserInfo register(@RequestBody UserInfo userInfo) {
        return userServiceImp.addUserInfo(userInfo);
    }

}

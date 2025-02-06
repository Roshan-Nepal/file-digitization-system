package com.roshan.filedigitizationsystem.dto.request;

import com.roshan.filedigitizationsystem.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String username;
    private String email;
    private String password;
    private Set<UserRole> roles;
}

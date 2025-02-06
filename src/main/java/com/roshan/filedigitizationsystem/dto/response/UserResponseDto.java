package com.roshan.filedigitizationsystem.dto.response;

import com.roshan.filedigitizationsystem.enums.UserRole;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Set<UserRole> roles;
}

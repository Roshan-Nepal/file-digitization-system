package com.roshan.filedigitizationsystem.dto.request;

import com.roshan.filedigitizationsystem.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank(message = "Username cannot be empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?: [a-zA-Z]){5,20}",
    message = "Username must only contain letters and spaces, with at least 5 characters ")
    private String username;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Please enter a valid email")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>])[a-zA-Z\\d!@#$%^&*(),.?\":{}|<>]{8,20}$",
            message = "Password should contain at least one letter, one number, one special character, and be 8-20 characters long")
    private String password;
    private Set<UserRole> roles;
}

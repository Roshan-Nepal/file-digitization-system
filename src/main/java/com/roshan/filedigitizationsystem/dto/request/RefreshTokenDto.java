package com.roshan.filedigitizationsystem.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RefreshTokenDto {
    @NotNull(message = "Refresh token cannot be null")
    private String refreshToken;
}

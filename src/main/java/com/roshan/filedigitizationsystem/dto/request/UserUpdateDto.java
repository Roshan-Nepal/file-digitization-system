package com.roshan.filedigitizationsystem.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto<T> {
    T t;
    String operation;
}

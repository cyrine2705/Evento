package com.app.evento.dto;

import com.app.evento.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private String image;
    private String password;
    private Role role;
}

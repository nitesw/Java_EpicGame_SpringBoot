package org.example.dto.user;

import lombok.Data;

@Data
public class CreateUserDto {
    private String username;
    private String email;
    private String password_hash;
}

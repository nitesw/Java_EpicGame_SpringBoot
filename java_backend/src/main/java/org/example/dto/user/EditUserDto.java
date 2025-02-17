package org.example.dto.user;

import lombok.Data;

@Data
public class EditUserDto {
    private int id;

    private String username;

    private String email;

    private String password_hash;
}

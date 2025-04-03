package org.example.controller;

import org.example.dto.user.EditUserDto;
import org.example.dto.user.UserDto;
import org.example.entities.UserEntity;
import org.example.repository.IUserRepository;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private UserService service;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return service.getList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable int id) {
        UserDto user = service.getById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public UserEntity updateUser(EditUserDto updatedUser) {
        return service.edit(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id){
        service.delete(id);
    }
}

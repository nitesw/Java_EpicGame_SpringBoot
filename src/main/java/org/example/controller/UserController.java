package org.example.controller;

import org.example.dto.user.CreateUserDto;
import org.example.dto.user.EditUserDto;
import org.example.entities.User;
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
    public List<User> getAllUsers() {
        return service.getList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id) {
        User user = service.getById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public User createUser(CreateUserDto user) {
        return service.create(user);
    }

    @PutMapping
    public User updateUser(EditUserDto updatedUser) {
        return service.edit(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(int id){
        service.delete(id);
    }
}

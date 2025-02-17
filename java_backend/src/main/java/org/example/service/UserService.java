package org.example.service;

import org.example.dto.user.CreateUserDto;
import org.example.dto.user.EditUserDto;
import org.example.entities.User;
import org.example.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserRepository repository;

    public List<User> getList() {
        return repository.findAll();
    }

    public User getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public User create(CreateUserDto userDto) {
        User entity = new User();
        entity.setCreated_at(LocalDate.now());
        entity.setUsername(userDto.getUsername());
        entity.setEmail(userDto.getEmail());
        entity.setPassword_hash(userDto.getPassword_hash());
        repository.save(entity);
        return entity;
    }

    public User edit(EditUserDto userDto) {
        User entity = repository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (userDto.getUsername() != null && !userDto.getUsername().isBlank()) {
            entity.setUsername(userDto.getUsername());
        }
        if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
            entity.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword_hash() != null && !userDto.getPassword_hash().isBlank()) {
            entity.setPassword_hash(userDto.getPassword_hash());
        }
        return repository.save(entity);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}

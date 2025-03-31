package org.example.service;

import org.example.dto.user.CreateUserDto;
import org.example.dto.user.EditUserDto;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.entities.UserRole;
import org.example.repository.IRoleRepository;
import org.example.repository.IUserRepository;
import org.example.repository.IUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private IUserRepository repository;
    private IRoleRepository roleRepository;
    private IUserRoleRepository userRoleRepository;

    public List<User> getList() {
        return repository.findAll();
    }

    public User getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public User create(CreateUserDto userDto) {
        User entity = new User();
        entity.setUsername(userDto.getUsername());
        entity.setPassword(userDto.getPassword());

        entity = repository.save(entity);

        Role role = roleRepository.findByName("USER").orElseThrow();
        UserRole userRole = new UserRole(null, entity, role);
        userRoleRepository.save(userRole);
        return entity;
    }

    public User edit(EditUserDto userDto) {
        User entity = repository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (userDto.getUsername() != null && !userDto.getUsername().isBlank()) {
            entity.setUsername(userDto.getUsername());
        }
        if (userDto.getUsername() != null && !userDto.getUsername().isBlank()) {
            entity.setUsername(userDto.getUsername());
        }
        if (userDto.getPassword() != null && !userDto.getPassword().isBlank()) {
            entity.setPassword(userDto.getPassword());
        }
        return repository.save(entity);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}

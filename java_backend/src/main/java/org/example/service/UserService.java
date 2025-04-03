package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.config.security.JwtService;
import org.example.dto.user.EditUserDto;
import org.example.dto.user.UserAuthDto;
import org.example.dto.user.UserDto;
import org.example.dto.user.UserRegisterDto;
import org.example.entities.UserEntity;
import org.example.mapper.IUserMapper;
import org.example.repository.IRoleRepository;
import org.example.repository.IUserRepository;
import org.example.repository.IUserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository repository;
    private final IUserMapper mapper;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public List<UserDto> getList() {
        return mapper.toDto(repository.findAll());
    }

    public UserDto getById(int id) {
        return mapper.toDto(repository.findById(id).orElse(null));
    }

    public UserEntity edit(EditUserDto userDto) {
        UserEntity entity = repository.findById(userDto.getId())
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

    public void register(UserRegisterDto userDto){
        if (repository.existsByUsername(userDto.getUsername())){
            throw new RuntimeException("User with the same username is already exists!");
        }

        var user = new UserEntity();

        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        repository.save(user);
    }

    public String authenticate(UserAuthDto userAuthDto) {
        UserEntity user = repository
                .findByUsername(userAuthDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User is not found!"));

        if (!passwordEncoder.matches(userAuthDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password!");
        }

        return jwtService.generateAccessToken(user);
    }
}

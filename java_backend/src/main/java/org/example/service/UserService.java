package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {
    private final IUserRepository repository;
    private final IUserMapper mapper;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Value("${google.api.userinfo}")
    private String googleUserInfoUrl;

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

    public String singInGoogle(String access_token) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + access_token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(googleUserInfoUrl, HttpMethod.GET, entity, String.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> userInfo = objectMapper.readValue(response.getBody(), new TypeReference<Map<String, String>>() {});
            var userEntity = repository.findByUsername(userInfo.get("username")).orElse(null);
            if(userEntity == null) {
                userEntity = new UserEntity();
                userEntity.setUsername(userInfo.get("username"));
                userEntity.setPassword("");
                repository.save(userEntity);
            }
            var jwt = jwtService.generateAccessToken(userEntity);
            return jwt;
        }
        return null;
    }
}

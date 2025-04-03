package org.example.repository;

import org.example.entities.UserEntity;
import org.example.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IUserRoleRepository extends JpaRepository<UserRole, Integer> {
    List<UserRole> findByUser(UserEntity user);
}

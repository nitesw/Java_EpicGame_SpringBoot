package org.example.seeder;

import lombok.AllArgsConstructor;
import org.example.entities.Role;
import org.example.repository.IRoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class RoleSeeder {
    private IRoleRepository roleRepository;

    public void seedRoles() {
        if(roleRepository.count() == 0){
            Role adminRole = new Role(null, "ADMIN");
            Role userRole = new Role(null, "USER");
            roleRepository.saveAll(List.of(adminRole, userRole));
        }
    }
}

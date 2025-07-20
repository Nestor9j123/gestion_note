package com.chance.coupchance.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chance.coupchance.Entites.Role;
import com.chance.coupchance.Enum.Roles;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    
        Optional<Role> findByLibelle(Roles libelle);
    
    Role findByLibelle(String libelle);

}

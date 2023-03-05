package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.ERole;
import fr.picom.picomspring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDAO extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}

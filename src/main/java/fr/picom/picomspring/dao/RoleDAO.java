package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Long> {
}
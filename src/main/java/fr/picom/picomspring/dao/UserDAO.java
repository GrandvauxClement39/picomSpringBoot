package fr.picom.picomspring.dao;

import fr.picom.picomspring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Long> {
}
package fr.picom.picomspring.service;

import fr.picom.picomspring.config.AuthResponse;
import fr.picom.picomspring.dto.UserDTO;
import fr.picom.picomspring.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User register(UserDTO userDTO);

    User login(String email, String password);

    List<User> findAll();

    User findById(Long id);

    Optional<User> findByEmail(String name);

    boolean deleteById(Long id);

    User update(User user);

    ResponseEntity<AuthResponse> authenticationProcess(String email, String password, boolean isRegister) throws AuthenticationException;
}

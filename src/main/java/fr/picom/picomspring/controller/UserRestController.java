package fr.picom.picomspring.controller;

import fr.picom.picomspring.config.AuthResponse;
import fr.picom.picomspring.dto.UserDTO;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserRestController {

    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/user")
    public List<User> getAllUser(){
        return userService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/api/user/{id}")
    public User findUserById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PostMapping(path = "/auth/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserDTO userDTO){
        User user = userService.register(userDTO);
        return userService.authenticationProcess(user.getEmail(), userDTO.getPassword(), true);
    }

}

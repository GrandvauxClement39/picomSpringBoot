package fr.picom.picomspring.controller;

import fr.picom.picomspring.dto.UserDTO;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return userService.finById(id);
    }

    @PostMapping(path = "/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserDTO user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(user));
    }

}

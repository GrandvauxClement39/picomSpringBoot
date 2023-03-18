package fr.picom.picomspring.controller;

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
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/user")
    public List<User> getAllUser(){
        return userService.findAll();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CUSTOMER')")
    @GetMapping("/api/user/{id}")
    public User findUserById(@PathVariable Long id){
        return userService.finById(id);
    }

    @PostMapping(path = "/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user){
        User newUser = new User();
        System.out.println("user"+ user.getCity());
        return ResponseEntity.ok().body(userService.register(user));
    }

}

package fr.picom.picomspring.controller;

import fr.picom.picomspring.config.AuthRequest;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserRestController {

    private UserService userService;

    @GetMapping("/api/user")
    public List<User> getAllUser(){
        return userService.findAll();
    }

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

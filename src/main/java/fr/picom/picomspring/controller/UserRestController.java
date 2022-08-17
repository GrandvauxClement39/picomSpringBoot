package fr.picom.picomspring.controller;

import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User register(@RequestBody User user){
        return userService.register(user);
    }

}

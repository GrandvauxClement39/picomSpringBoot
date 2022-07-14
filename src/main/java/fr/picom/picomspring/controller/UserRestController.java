package fr.picom.picomspring.controller;

import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;

    @GetMapping("/user")
    public List<User> getAllUser(){
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public User findUserById(@PathVariable Long id){
        return userService.finById(id);
    }

    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User register(@RequestBody User user){
        return userService.register(user);
    }

    @PostMapping("/user/login")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User login(@RequestBody String email, @RequestBody String password){
        return userService.login(email, password);
    }
}

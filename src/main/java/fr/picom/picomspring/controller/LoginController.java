package fr.picom.picomspring.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class LoginController {

    @RolesAllowed("CUSTOMER")
    @RequestMapping("/*")
    public String getUser(){
        return "Welcome user !";
    }

    @RolesAllowed({"CUSTOMER", "ADMIN"})
    @RequestMapping("/admin")
    public String getAdmin(){
        return "Welcome admin";
    }

}

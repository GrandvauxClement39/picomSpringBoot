package fr.picom.picomspring.config;

import fr.picom.picomspring.model.Role;

import java.util.List;
import java.util.Set;

public class AuthResponse {
    private Long id;
    private String email;
    private List<String> roles;

    public AuthResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public AuthResponse(Long id, String email, List<String> roles) {
        this.id = id;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}

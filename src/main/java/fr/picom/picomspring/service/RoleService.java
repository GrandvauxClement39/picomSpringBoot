package fr.picom.picomspring.service;

import fr.picom.picomspring.model.Role;

import java.util.List;

public interface RoleService {

    Role add(Role role);

    List<Role> findAll();

    Role findById(Long id);
}

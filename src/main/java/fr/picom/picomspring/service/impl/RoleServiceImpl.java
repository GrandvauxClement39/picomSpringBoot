package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.RoleDAO;
import fr.picom.picomspring.model.Role;
import fr.picom.picomspring.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDAO) {
        super();
        this.roleDAO = roleDAO;
    }

    public Role add(Role role) {
        return roleDAO.save(role);
    }

    public List<Role> findAll() {
        return roleDAO.findAll();
    }

    public Role finById(Long id){
        return roleDAO.findById(id).orElse(null);
    }

}

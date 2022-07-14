package fr.picom.picomspring.service;

import fr.picom.picomspring.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    User login(String email, String password);

    List<User> findAll();

    User finById(Long id);

    User findByEmail(String name);

    boolean deleteById(Long id);

    User update(User user);
}

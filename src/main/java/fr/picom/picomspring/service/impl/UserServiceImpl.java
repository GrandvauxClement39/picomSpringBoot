package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.UserDAO;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        super();
        this.userDAO = userDAO;
    }

    public User register(User user) {
        return userDAO.save(user);
    }

    public User login(String email, String password){
        return userDAO.findByEmailAndPassword(email, password);
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User finById(Long id){
        return userDAO.findById(id).orElse(null);
    }

    public Optional<User> findByEmail(String email){
        return userDAO.findByEmail(email);
    }

    public boolean deleteById(Long id){
        if (userDAO.existsById(id)){
            userDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public User update(User user){
        if (userDAO.existsById(user.getId())){
            return userDAO.save(user);
        } else {
            return null;
        }
    }
}

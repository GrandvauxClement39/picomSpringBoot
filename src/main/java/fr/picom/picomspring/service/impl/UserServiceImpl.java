package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.CityDAO;
import fr.picom.picomspring.dao.UserDAO;
import fr.picom.picomspring.model.City;
import fr.picom.picomspring.model.Country;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.CityService;
import fr.picom.picomspring.service.CountryService;
import fr.picom.picomspring.service.RoleService;
import fr.picom.picomspring.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    private CityService cityService;

    private CountryService countryService;

    private RoleService roleService;

    public UserServiceImpl(UserDAO userDAO, CityService cityService, CountryService countryService, RoleService roleService) {
        super();
        this.userDAO = userDAO;
        this.cityService = cityService;
        this.countryService = countryService;
        this.roleService = roleService;
    }

    public User register(User user) {
        if (user.getCity().getId() == null){
            City city = cityService.findByName(user.getCity().getName());
            System.out.println("city find "+ city);
            if (city == null){
                Country france = countryService.findByName("France");
                City newCity = new City();
                newCity.setCountry(france);
                newCity.setName(user.getCity().getName());
                city = cityService.add(newCity);
            }
            user.setCity(city);
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        System.out.println("Je suis juste avant ajout user -------------------");
        user.setRole(roleService.finById(2L));
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

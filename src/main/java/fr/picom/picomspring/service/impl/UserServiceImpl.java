package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.dao.UserDAO;
import fr.picom.picomspring.dto.UserDTO;
import fr.picom.picomspring.exceptions.EmailAlreadyExistsException;
import fr.picom.picomspring.model.City;
import fr.picom.picomspring.model.Country;
import fr.picom.picomspring.model.Role;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.service.CityService;
import fr.picom.picomspring.service.CountryService;
import fr.picom.picomspring.service.RoleService;
import fr.picom.picomspring.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public User register(UserDTO userDTO) {
        try {
            // Check if email already use
            boolean emailAlreadyUse = userDAO.findByEmail(userDTO.getEmail()).isPresent();
                  //  .orElseThrow(() -> new EmailAlreadyExistsException("Cet Email est déjà utilisé"));
            if (emailAlreadyUse) {
                throw new EmailAlreadyExistsException("Cet Email est déjà utilisé");
            }
            City city = cityService.findByName(userDTO.getCity());

            if (city == null){
                Country france = countryService.findByName("France");
                City newCity = new City();
                newCity.setCountry(france);
                newCity.setName(userDTO.getCity());
                city = cityService.add(newCity);
            }

            User user = new User();
            user.setEmail(userDTO.getEmail());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setRoadName(userDTO.getRoadName());
            user.setPostalCode(userDTO.getPostalCode());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setVerified(false);
            user.setCompanyName(userDTO.getCompanyName());
            user.setNumSiret(userDTO.getNumSiret());
            user.setCity(city);

            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findById(2L));
            user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
            user.setRoles(roles);
            return userDAO.save(user);
        } catch (DataIntegrityViolationException e){
            throw new IllegalArgumentException("Les champs renseigné sont pas bon");
        }
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

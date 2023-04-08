package fr.picom.picomspring.service.impl;

import fr.picom.picomspring.config.AuthResponse;
import fr.picom.picomspring.dao.UserDAO;
import fr.picom.picomspring.dto.UserDTO;
import fr.picom.picomspring.exceptions.EmailAlreadyExistsException;
import fr.picom.picomspring.model.City;
import fr.picom.picomspring.model.Country;
import fr.picom.picomspring.model.Role;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.security.jwt.JwtUtils;
import fr.picom.picomspring.security.services.UserDetailsImpl;
import fr.picom.picomspring.service.CityService;
import fr.picom.picomspring.service.CountryService;
import fr.picom.picomspring.service.RoleService;
import fr.picom.picomspring.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    private CityService cityService;

    private CountryService countryService;

    private RoleService roleService;

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

    public UserServiceImpl(UserDAO userDAO, CityService cityService, CountryService countryService, RoleService roleService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        super();
        this.userDAO = userDAO;
        this.cityService = cityService;
        this.countryService = countryService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public User register(UserDTO userDTO) {
        try {
            // Check if email already use
            boolean emailAlreadyUse = userDAO.findByEmail(userDTO.getEmail()).isPresent();
            if (emailAlreadyUse) {
                throw new EmailAlreadyExistsException("Cet Email est déjà utilisé");
            }
            City city = cityService.findByName(userDTO.getCity());

            if (city == null) {
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
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Les champs renseigné sont pas bon");
        }
    }

    public User login(String email, String password) {
        return userDAO.findByEmailAndPassword(email, password);
    }

    public List<User> findAll() {
        return userDAO.findAll();
    }

    public User finById(Long id) {
        return userDAO.findById(id).orElse(null);
    }

    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public boolean deleteById(Long id) {
        if (userDAO.existsById(id)) {
            userDAO.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public User update(User user) {
        if (userDAO.existsById(user.getId())) {
            return userDAO.save(user);
        } else {
            return null;
        }
    }

    public ResponseEntity<AuthResponse> authenticationProcess(String email, String password, boolean isRegister)
            throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(
                            new UsernamePasswordAuthenticationToken(email, password)
                    );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            AuthResponse response = new AuthResponse(userDetails.getId(), userDetails.getEmail(), roles);
            if (isRegister){
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                        .body(response);
            } else {
                return ResponseEntity
                        .ok()
                        .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                        .body(response);
            }
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

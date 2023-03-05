package fr.picom.picomspring.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import fr.picom.picomspring.config.AuthRequest;
import fr.picom.picomspring.config.AuthResponse;
import fr.picom.picomspring.exceptions.AuthenticationException;
import fr.picom.picomspring.model.User;
import fr.picom.picomspring.security.jwt.AuthTokenFilter;
import fr.picom.picomspring.security.jwt.JwtUtils;
import fr.picom.picomspring.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class JwtAuthRestController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthTokenFilter jwtTokenFilter;

    @RequestMapping(value = "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request)
            throws AuthenticationException {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            AuthResponse response = new AuthResponse(
                    userDetails.getId(),
                    userDetails.getEmail(),
                    roles
            );
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                    .body(response);

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

   /*@RequestMapping(value = "/auth/refresh", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = jwtTokenFilter.getAccessToken(request);
        final String token = authToken.substring(7);
        String username = jwtUtil.getUsernameFromToken(token);
        //JwtUserDetails user = (JwtUserDetails) jwtUtil.loadUserByUsername(username);

        if (jwtUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtUtil.refreshToken(token);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setAccessToken(refreshedToken);
            return ResponseEntity.ok(authResponse);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }*/

    /* @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    private void authenticate(String username, String password) {
        System.out.println("Username !!! :"+username + password);
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new AuthenticationException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println(e.toString());
            throw new AuthenticationException("INVALID_CREDENTIALS", e);
        }
    }*/
}

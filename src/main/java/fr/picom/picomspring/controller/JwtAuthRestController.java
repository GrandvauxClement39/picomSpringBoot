package fr.picom.picomspring.controller;

import javax.validation.Valid;
import fr.picom.picomspring.config.AuthRequest;
import fr.picom.picomspring.config.AuthResponse;
import fr.picom.picomspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtAuthRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {

       return userService.authenticationProcess(
               request.getEmail(),
               request.getPassword(),
               false);
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
}

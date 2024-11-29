package com.omnitrust.rfm.service;

import com.omnitrust.rfm.config.UserPrincipal;
import com.omnitrust.rfm.domain.User;
import com.omnitrust.rfm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;


    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        return user;
    }

    public ResponseEntity<?> verify(User user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        if (auth.isAuthenticated()) {
            String token = jwtService.generateToken(user.getUsername());


            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            return ResponseEntity.accepted().body(map);
        }
        return ResponseEntity.badRequest().build();
    }

    public boolean updatePassword(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        }
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }

    private User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getCurrentUser() {

        return findByUsername(
                ((UserPrincipal) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal())
                        .getUsername()
        );
    }
}

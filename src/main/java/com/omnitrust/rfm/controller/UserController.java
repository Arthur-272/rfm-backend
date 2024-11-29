package com.omnitrust.rfm.controller;

import com.omnitrust.rfm.domain.User;
import com.omnitrust.rfm.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        User registeredUser;
        try {
            registeredUser = userService.registerUser(user);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        return userService.verify(user);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser() {
        User user = userService.getCurrentUser();
        if(user != null) {
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.noContent().build();

    }
}

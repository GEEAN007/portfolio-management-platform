package com.gyana.portfolio.controller;

import com.gyana.portfolio.config.JwtUtil;
import com.gyana.portfolio.entity.User;
import com.gyana.portfolio.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserRepository userRepository;
private final BCryptPasswordEncoder passwordEncoder;
    public AuthController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
public String register(@RequestBody User user) {

    if (userRepository.findByEmail(user.getEmail()) != null) {
        return "User already exists";
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    if (user.getEmail().equals("admin@test.com")) {
        user.setRole("ROLE_ADMIN");
    } else {
        user.setRole("ROLE_USER");
    }

    userRepository.save(user);

    return "User registered successfully";
}

    @PostMapping("/login")
public String login(@RequestBody User user) {

    User existing = userRepository.findByEmail(user.getEmail());

    System.out.println("=== LOGIN DEBUG ===");
    System.out.println("Entered Email: " + user.getEmail());
    System.out.println("Entered Password: " + user.getPassword());

    if (existing == null) {
        System.out.println("User NOT found in DB");
        return "Invalid credentials";
    }

    System.out.println("Stored Hash: " + existing.getPassword());

    boolean match = passwordEncoder.matches(user.getPassword(), existing.getPassword());
    System.out.println("Password Match Result: " + match);

    if (match) {
        return JwtUtil.generateToken(existing.getEmail());
    }

    return "Invalid credentials";
}
}
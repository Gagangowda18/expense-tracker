package com.gagan.expensetracker.service;

import com.gagan.expensetracker.dto.AuthResponse;
import com.gagan.expensetracker.dto.LoginRequest;
import com.gagan.expensetracker.dto.SignupRequest;
import com.gagan.expensetracker.model.User;
import com.gagan.expensetracker.repository.UserRepository;
import com.gagan.expensetracker.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(SignupRequest req) {
        if (userRepository.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Email already in use");
        }
        User user = User.builder()
                .name(req.name())
                .email(req.email())
                .password(passwordEncoder.encode(req.password()))
                .build();
        userRepository.save(user);
    }

    public AuthResponse authenticate(LoginRequest req) {
        User user = userRepository.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getName(), user.getEmail());
        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail());
    }

    public AuthResponse registerAndAuthenticate(SignupRequest req) {
    // 1. Check if user exists
    if (userRepository.existsByEmail(req.email())) {
        throw new IllegalArgumentException("Email already in use");
    }

    // 2. Save new user
    User user = User.builder()
            .name(req.name())
            .email(req.email())
            .password(passwordEncoder.encode(req.password()))
            .build();

    userRepository.save(user);

    // 3. Generate JWT
    String token = jwtUtil.generateToken(user.getId(), user.getName(), user.getEmail());

    // 4. Return response JSON
    return new AuthResponse(token, user.getId(), user.getName(), user.getEmail());
}

}

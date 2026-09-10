package com.sdetcommerce.backend.service;

import com.sdetcommerce.backend.dto.UserRegistrationRequest;
import com.sdetcommerce.backend.dto.UserResponse;
import com.sdetcommerce.backend.entity.User;
import com.sdetcommerce.backend.exception.EmailAlreadyExistsException;
import com.sdetcommerce.backend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.sdetcommerce.backend.dto.LoginRequest;
import com.sdetcommerce.backend.dto.LoginResponse;
import com.sdetcommerce.backend.exception.InvalidCredentialsException;
import com.sdetcommerce.backend.security.JwtService;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        JwtService jwtService) {

    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
}

    public UserResponse registerUser(UserRegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(
                request.getName(),
                request.getEmail(),
                hashedPassword
        );

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }
    public LoginResponse loginUser(LoginRequest request) {

    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() ->
                    new InvalidCredentialsException(
                            "Invalid email or password"
                    )
            );

    if (!passwordEncoder.matches(
            request.getPassword(),
            user.getPassword())) {

        throw new InvalidCredentialsException(
                "Invalid email or password"
        );
    }

   String token = jwtService.generateToken(
        user.getEmail(),
        user.getId(),
        user.getRole().name()
);

return new LoginResponse(
        user.getId(),
        user.getName(),
        user.getEmail(),
        "Login successful",
        token
);
}
public UserResponse getUserProfile(String email) {

    User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                    new InvalidCredentialsException("User not found")
            );

    return new UserResponse(
            user.getId(),
            user.getName(),
            user.getEmail()
    );
}
}
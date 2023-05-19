package com.binar.securityspringboot.auth;


import com.binar.securityspringboot.configuration.JwtService;
import com.binar.securityspringboot.model.Role;
import com.binar.securityspringboot.model.User;
import com.binar.securityspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public boolean isEmailExists(String email) {
        return repository.existsByEmail(email);
    }

    public AuthenticationResponse register(RegisterRequest request) {


        if (isEmailExists(request.getEmail())) throw new IllegalArgumentException("email taken");

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ADMIN")
                .build();

        repository.save(user);

        var JwtToken = jwtService.generatedToken(user);

        return AuthenticationResponse.builder()
                .token(JwtToken).build();
    }

    public AuthenticationResponse registerUser(RegisterRequest request) {


        if (isEmailExists(request.getEmail())) throw new IllegalArgumentException("email taken");

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("CUSTOMER")
                .build();

        repository.save(user);

        var JwtToken = jwtService.generatedToken(user);

        return AuthenticationResponse.builder()
                .token(JwtToken).build();
    }

    public AuthenticationResponse update(User request) {

        User isEmailValid = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("email tidak ditemukan"));

        isEmailValid.setId(request.getId());
        isEmailValid.setEmail(request.getEmail());
        isEmailValid.setUsername(request.getUsername());
        isEmailValid.setPassword(request.getPassword());
        isEmailValid.setRole(request.getRole());

        User save = repository.save(isEmailValid);

        var JwtToken = jwtService.generatedToken(save);

        return AuthenticationResponse.builder()
                .token(JwtToken).build();
    }
}

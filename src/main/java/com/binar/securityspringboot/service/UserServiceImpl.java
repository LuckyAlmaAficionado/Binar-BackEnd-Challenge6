package com.binar.securityspringboot.service;

import com.binar.securityspringboot.auth.AuthenticationResponse;
import com.binar.securityspringboot.auth.AuthenticationService;
import com.binar.securityspringboot.auth.RegisterRequest;
import com.binar.securityspringboot.model.Role;
import com.binar.securityspringboot.model.User;
import com.binar.securityspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthenticationService authenticationService;

    public String updateUser(
            Long id,
            String username,
            String email,
            String password,
            Role role
    ) {
        User updateUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("email tidak ditemukan"));

        String token = String.valueOf(authenticationService.update(updateUser));

        return "berhasil update new token " + token;
    }

    public String deleteUserById(Long id) {

        userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id tidak ditemukan"));

        userRepository.deleteById(id);

        return "data berhasil dihapus";
    }
}

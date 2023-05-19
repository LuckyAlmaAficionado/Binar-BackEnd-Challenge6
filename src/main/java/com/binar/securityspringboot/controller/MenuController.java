package com.binar.securityspringboot.controller;

import com.binar.securityspringboot.auth.AuthenticationResponse;
import com.binar.securityspringboot.auth.AuthenticationService;
import com.binar.securityspringboot.auth.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MenuController {

    private final AuthenticationService service;

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/page")
    public String login1(Model model) {
        model.addAttribute("registerRequestForm", new RegisterRequest());
        model.addAttribute("message", "");
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request, Model model) {
        ResponseEntity<AuthenticationResponse> ok = ResponseEntity.ok(service.register(request));// todo -> membuat akun baru dari interface yang sudah disediakan
        model.addAttribute("registerRequestForm", new RegisterRequest());
        model.addAttribute("message", "");
        return "register";
    }

    @GetMapping("/lmao")
    public String lmao() {
        return "text";
    }


}

package com.binar.securityspringboot.controller;

import com.binar.securityspringboot.model.Role;
import com.binar.securityspringboot.service.BookingServiceImpl;
import com.binar.securityspringboot.service.UserServiceImpl;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("api/v1/customer")
public class CustomersController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookingServiceImpl bookingService;

    @PutMapping("/update")
    @CrossOrigin
    private String updateUser(
            @RequestParam Long id,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam Role role
    ) {
        return userService.updateUser(id, username, email, password, role);
    }

    @CrossOrigin
    @DeleteMapping("/deleteUser")
    private String deleteUser(
            @RequestParam Long id
    ) {
        return userService.deleteUserById(id);
    }

    @CrossOrigin
    @PostMapping("/invoice")
    private String getInvoice(
            @RequestParam String bookingId
    ) throws JRException, FileNotFoundException {
        return bookingService.printReport(bookingId);
    }

}

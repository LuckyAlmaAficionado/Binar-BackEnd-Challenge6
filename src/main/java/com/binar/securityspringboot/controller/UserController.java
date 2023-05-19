package com.binar.securityspringboot.controller;

import com.binar.securityspringboot.model.Booking;
import com.binar.securityspringboot.model.Movie;
import com.binar.securityspringboot.model.Schedule;
import com.binar.securityspringboot.service.BookingServiceImpl;
import com.binar.securityspringboot.service.MovieServiceImpl;
import com.binar.securityspringboot.service.ScheduleServiceImpl;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@EnableMethodSecurity
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private BookingServiceImpl bookingService;
    @Autowired
    private MovieServiceImpl movieService;
    @Autowired
    private ScheduleServiceImpl scheduleService;

    @GetMapping("/role")
    @CrossOrigin
    public String getRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
    }

    @PostMapping("/booking")
    @CrossOrigin
    public Booking postBooking(@RequestParam Long movieId,
                               @RequestParam Long scheduleId,
                               @RequestParam String[] seat) throws JRException, FileNotFoundException {
        return bookingService.postBooking(movieId, scheduleId, seat);
    }

    @GetMapping("/tayang")
    @CrossOrigin
    public List<Movie> getMovieShowing() {
        return movieService.getMovieShowing();
    }

    @GetMapping("/movieSchedule")
    @CrossOrigin
    public List<Schedule> getScheduleByMovie(
            @RequestParam Long movieFk
    ) {
        return scheduleService.getScheduleByMovie(movieFk);
    }

}

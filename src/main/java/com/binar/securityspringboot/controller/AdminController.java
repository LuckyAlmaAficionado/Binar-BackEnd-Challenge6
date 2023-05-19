package com.binar.securityspringboot.controller;

import com.binar.securityspringboot.model.Movie;
import com.binar.securityspringboot.service.MovieServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableMethodSecurity
@RequestMapping("/api/v1/admin")
public class AdminController {

    private String authorities = null;
    @Autowired
    private MovieServiceImpl service;

    @PostMapping("/addNewMovieBody")
    @PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    public Movie addNewMovieBody(
            @RequestBody Movie movie
    ) {
        return service.addNewMovieBody(movie);
    }


    @PostMapping("/addNewMovie")
    @PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    public Movie addNewMovie(@RequestParam String movieName,
                             @RequestParam String genre,
                             @RequestParam Boolean isShowing) {
        return service.addNewMovie(movieName, genre, isShowing);
    }

    @PutMapping("/updateMovie")
    @PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    public Movie updateMovie(@RequestParam Long movieId,
                             @RequestParam String movieName,
                             @RequestParam String genre,
                             @RequestParam Boolean isShowing) {
        return service.updateMovie(movieId, movieName, genre, isShowing);
    }

    @DeleteMapping("/deleteMovie")
    @PreAuthorize("hasAuthority('ADMIN')")
    @CrossOrigin
    public String deleteMovie(@RequestParam Long movieId) {
        return service.deleteMovie(movieId);
    }
}

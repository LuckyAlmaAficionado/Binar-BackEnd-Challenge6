package com.binar.securityspringboot.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Movie {
    @Id
    @SequenceGenerator(
            name = "movie_sequence",
            sequenceName = "movie_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "movie_sequence"
    )
    private Long movieId;
    private String movieName;
    private String genre;
    private Boolean isShowing;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "movieFk", referencedColumnName = "movieId", insertable = true, updatable = true)
    private List<Schedule> schedulesList;

    public Movie(Long movieId, String movieName, String genre, Boolean isShowing) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.genre = genre;
        this.isShowing = isShowing;
    }

    public Movie(String movieName, String genre, Boolean isShowing) {
        this.movieName = movieName;
        this.genre = genre;
        this.isShowing = isShowing;
    }
}

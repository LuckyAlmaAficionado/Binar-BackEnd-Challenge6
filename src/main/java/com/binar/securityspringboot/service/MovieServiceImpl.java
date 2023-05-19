package com.binar.securityspringboot.service;

import com.binar.securityspringboot.model.Booking;
import com.binar.securityspringboot.model.Movie;
import com.binar.securityspringboot.model.Schedule;
import com.binar.securityspringboot.repository.BookingRepository;
import com.binar.securityspringboot.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MovieServiceImpl {

    private final String MSG_MOVIE_EXISTS = "MOVIE WITH NAME %s EXISTS ALREADY";
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private BookingRepository bookingRepository;

    public Movie addNewMovie(String movieName, String genre, Boolean isShowing) {

        boolean isMovieExists = movieRepository.findByMovieName(movieName).isPresent();

        if (isMovieExists) throw new IllegalArgumentException(String.format(MSG_MOVIE_EXISTS, movieName));

        Movie movie = new Movie(
                movieName,
                genre,
                isShowing
        );

        return movieRepository.save(movie);
    }

    public Movie updateMovie(Long movieId, String movieName, String genre, Boolean isShowing) {

        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("film tidak ditemukan"));

        movie.setMovieName(movieName);
        movie.setGenre(genre);
        movie.setIsShowing(isShowing);

        return movieRepository.save(movie);
    }

    public String deleteMovie(Long movieId) {
        if (!movieRepository.findById(movieId).isPresent()) throw new IllegalArgumentException("film tidak ditemukan");
        movieRepository.deleteById(movieId);
        return "movie with id " + movieId + " deleted";
    }

    public Movie addNewMovieBody(Movie movie) {
        if (movieRepository.findByMovieName(movie.getMovieName()).isPresent()) throw new IllegalArgumentException("judul film sudah ada");
        return movieRepository.save(movie);
    }

    public void getSeat(List<Movie> movieList) {
        List<String> temp = new ArrayList<>();

        char character = 'A';
        int x = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 1; j <= 5; j++) {
                temp.add(character + String.valueOf(j));
            }
            character++;
        }

        temp.forEach(System.out::println);


        for (Movie scheduleList : movieList) {
            for (Schedule schedule : scheduleList.getSchedulesList()) {

                Long movieFk = schedule.getMovieFk();
                Long scheduleId = schedule.getScheduleId();

                List<Booking> bookings = bookingRepository.findBookingByMovieIdAndScheduleId(movieFk, scheduleId);

                for (Booking seat : bookings) {
                    log.info("kursi yang sudah dipesan: " + seat.getSeat());
                    temp.remove(seat.getSeat());
                }

                String seats[] = new String[temp.size()];
                for (int i = 0; i < temp.size(); i++) {
                    seats[i] = temp.get(i);
                }

                schedule.setSeats(seats);
            }
        }
    }

    public List<Movie> getMovieShowing() {
        List<Movie> all = movieRepository.findAll();

        getSeat(all);

        return all;
    }
}

package com.binar.securityspringboot.model;


import lombok.*;

import javax.persistence.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table
public class Booking {
    @Id
    @SequenceGenerator(name = "booking_sequence", sequenceName = "booking_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "booking_sequence")
    private Long bookingId;
    private String codeBooking;
    private Long movieId;
    private String movieName;
    private Long scheduleId;
    private String username;
    private String studio;
    private Time startTime;
    private Time endTime;
    private String date;
    private String seat;
    private Integer price;
    private Status status;
    private LocalDateTime lastUpdate;

    public Booking(String codeBooking, Long movieId, String movieName, Long scheduleId, String username, String studio, Time startTime, Time endTime, String date, String seat, Integer price, Status status) {
        this.codeBooking = codeBooking;
        this.movieId = movieId;
        this.movieName = movieName;
        this.scheduleId = scheduleId;
        this.username = username;
        this.studio = studio;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.seat = seat;
        this.price = price;
        this.status = status;
    }

    public Booking(Long movieId, String codeBooking, String movieName, String username, String studio) {
        this.movieId = movieId;
        this.codeBooking = codeBooking;
        this.movieName = movieName;
        this.username = username;
        this.studio = studio;
    }

    public LocalDateTime getLastUpdate() {
        return LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(bookingId, booking.bookingId) && Objects.equals(codeBooking, booking.codeBooking) && Objects.equals(movieId, booking.movieId) && Objects.equals(movieName, booking.movieName) && Objects.equals(scheduleId, booking.scheduleId) && Objects.equals(username, booking.username) && Objects.equals(studio, booking.studio) && Objects.equals(startTime, booking.startTime) && Objects.equals(endTime, booking.endTime) && Objects.equals(date, booking.date) && Objects.equals(seat, booking.seat) && Objects.equals(price, booking.price) && status == booking.status && Objects.equals(lastUpdate, booking.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, codeBooking, movieId, movieName, scheduleId, username, studio, startTime, endTime, date, seat, price, status, lastUpdate);
    }

    @Override
    public String toString() {
        return "Booking{" + "bookingId=" + bookingId + ", codeBooking='" + codeBooking + '\'' + ", movieId=" + movieId + ", movieName='" + movieName + '\'' + ", scheduleId=" + scheduleId + ", username='" + username + '\'' + ", studio='" + studio + '\'' + ", startTime=" + startTime + ", endTime=" + endTime + ", date=" + date + ", seat='" + seat + '\'' + ", price=" + price + ", status=" + status + ", lastUpdate=" + lastUpdate + '}';
    }
}

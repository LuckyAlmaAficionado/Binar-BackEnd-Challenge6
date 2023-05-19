package com.binar.securityspringboot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Jasper {
    String codeBooking;
    String cinemaName;
    String movieName;
    String name;
    LocalDateTime startTime;
    LocalDateTime endTime;
    LocalDate date;
    String seat;
    Integer price;

    public Jasper(String codeBooking, String movieName, String name, LocalDateTime startTime, LocalDateTime endTime, LocalDate date, String seat, Integer price) {
        this.codeBooking = codeBooking;
        this.movieName = movieName;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.seat = seat;
        this.price = price;
    }
}

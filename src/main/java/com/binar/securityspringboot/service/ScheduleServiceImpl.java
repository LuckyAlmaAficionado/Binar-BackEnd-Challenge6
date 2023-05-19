package com.binar.securityspringboot.service;


import com.binar.securityspringboot.model.Booking;
import com.binar.securityspringboot.model.Schedule;
import com.binar.securityspringboot.repository.BookingRepository;
import com.binar.securityspringboot.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl {

    @Autowired
    private ScheduleRepository repository;

    @Autowired
    private BookingRepository bookingRepository;
    public void getSeat(List<Schedule> schedules) {
        for (Schedule seat : schedules) {
            List<String> temp = new ArrayList<>();
            char character = 'A';
            int x = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 1; j <= 5; j++) {
                    temp.add(character + String.valueOf(j));
                }
                character++;
            }

            Long movieFK = seat.getMovieFk();
            Long scheduleId = seat.getScheduleId();

            System.out.println("movieFK : " + movieFK + ", schedule Id: " + scheduleId);
            List<Booking> bookings = bookingRepository.findBookingByMovieIdAndScheduleId(movieFK, scheduleId);

            for (Booking kursi : bookings) {
                System.out.println("kursi yang sudah dipersan: " + kursi.getSeat());
                temp.remove(kursi.getSeat());
            }


            System.out.println(temp.size());
            String[] kursiKosong = new String[temp.size()];

            for (int i = 0; i < temp.size(); i++) {
                kursiKosong[i] = temp.get(i);
            }

            seat.setSeats(kursiKosong);
        }
    }
    public List<Schedule> getScheduleByMovie(Long movieFk) {
        Optional<Schedule> byMovieFk = repository.findByMovieFk(movieFk);
        List<Schedule> list = List.of(byMovieFk.orElse(null));

        if (!byMovieFk.isPresent()) throw new IllegalArgumentException("movie does not exists");

        getSeat(list);

        return list;
    }


}

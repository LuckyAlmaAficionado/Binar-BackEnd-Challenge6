package com.binar.securityspringboot.repository;

import com.binar.securityspringboot.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findBookingByMovieIdAndScheduleId(Long movieFk, Long scheduleId);

    Collection<Booking> findByCodeBooking(String bookingId);

    Collection<Booking> findBookingByMovieIdAndScheduleIdAndSeat(Long movieId, Long scheduleId, String seat);
}

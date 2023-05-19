package com.binar.securityspringboot.service;

import com.binar.securityspringboot.model.*;
import com.binar.securityspringboot.repository.BookingRepository;
import com.binar.securityspringboot.repository.MovieRepository;
import com.binar.securityspringboot.repository.ScheduleRepository;
import com.binar.securityspringboot.repository.UserRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class BookingServiceImpl {

    @Autowired
    private BookingRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private MovieRepository movieRepository;

    public String printReport(String bookingCode) throws FileNotFoundException, JRException {
//        String path = "D:\\code\\.springboot\\challenge 5 - Backup\\jasper";
        String path = "C:\\Users\\USER\\Documents\\jasper";

        Collection<Booking> isBookingCodeValid = repository.findByCodeBooking(bookingCode);

        if (isBookingCodeValid.isEmpty()) throw new FileNotFoundException("code booking tidak ditemukan");

        File file = ResourceUtils.getFile("classpath:jasper-report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(isBookingCodeValid);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Lucky Alma");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\" + bookingCode + ".pdf");
        return "report generate in path: " + path;
    }

    private String getRand() {
        String saltChar = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rand = new Random();
        while (salt.length() < 6) {
            int index = (int) (rand.nextFloat() * saltChar.length());
            salt.append(saltChar.charAt(index));
        }

        return salt.toString();
    }


    public Booking postBooking(Long movieId, Long scheduleId, String[] seats) {

        Movie movieDoesNotExists = movieRepository.findById(movieId).orElseThrow(() -> new IllegalArgumentException("movie does not exists"));

        Schedule scheduleTemp = scheduleRepository.findByScheduleId(scheduleId);

        String bookingCode = getRand().toUpperCase();

        Booking booking = null;

        if (seats.length > 1) {
            for (String seat : seats) {

                Collection<Booking> andSeat = repository.findBookingByMovieIdAndScheduleIdAndSeat(movieId, scheduleId, seat);

                if (!andSeat.isEmpty()) throw new IllegalArgumentException("seat " + seat + " reserved");

                booking = new Booking(bookingCode, movieId, movieDoesNotExists.getMovieName(), scheduleId, SecurityContextHolder.getContext().getAuthentication().getName().toString(),
                        scheduleTemp.getStudio(), scheduleTemp.getStartTime(), scheduleTemp.getEndTime(), String.valueOf(scheduleTemp.getDate()),
                        seat, scheduleTemp.getPrice(), Status.ON_PROCESS_PAYMENT);
                repository.save(booking);
            }
        } else {

            Collection<Booking> andSeat = repository.findBookingByMovieIdAndScheduleIdAndSeat(movieId, scheduleId, seats[0]);

            if (!andSeat.isEmpty()) throw new IllegalArgumentException("seat " + seats[0] + " reserved");

            booking = new Booking(bookingCode, movieId, movieDoesNotExists.getMovieName(), scheduleId, SecurityContextHolder.getContext().getAuthentication().getName().toString(),
                    scheduleTemp.getStudio(), scheduleTemp.getStartTime(), scheduleTemp.getEndTime(), String.valueOf(scheduleTemp.getDate()),
                    seats[0], scheduleTemp.getPrice(), Status.ON_PROCESS_PAYMENT);
            repository.save(booking);
        }

//        printReport(bookingCode);

        return booking;

    }
}

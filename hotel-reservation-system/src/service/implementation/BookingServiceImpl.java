package service.implementation;

import dao.BookingUtil;
import data.Booking;
import service.BookingService;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingServiceImpl implements BookingService {

    @Override
    public boolean book(Booking bookingDetails) throws Exception{
        BookingUtil bookingUtil = new BookingUtil();
        return bookingUtil.booking(bookingDetails);
    }

    @Override
    public boolean deleteBooking(String bookingId) {
        try {
            BookingUtil bookingUtil = new BookingUtil();
            bookingUtil.deleteBooking(bookingId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Booking> getBookingHistory() {
        try {
            BookingUtil bookingUtil = new BookingUtil();
            return bookingUtil.getAllBookings();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Booking getBookingById(String bookingId) throws Exception {
        BookingUtil bookingUtil = new BookingUtil();
        return bookingUtil.getBookingDetailsBasedOnId(bookingId);
    }

    @Override
    public boolean updateBooking(Booking booking) throws Exception {
        BookingUtil bookingUtil = new BookingUtil();
        return bookingUtil.updateBookingDetails(booking);
    }

    @Override
    public List<Booking> getBookingListByBookingDate(LocalDate date) {
        return getBookingHistory().stream()
                .filter(booking -> booking.getStartDate().equals(date))
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> getOccupiedBookingListByDate(LocalDate startDate, LocalDate endDate) {
        try {
            BookingUtil bookingUtil = new BookingUtil();
            return bookingUtil.getOccupiedBookingListByDate(startDate, endDate);
        } catch (Exception e) {
            return List.of();
        }
    }

}

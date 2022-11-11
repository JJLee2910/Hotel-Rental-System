package service;

import data.Booking;

import java.time.LocalDate;
import java.util.List;

/**
 * backend logic related to booking
 */
public interface BookingService {
    /**
     * book room
     * @param bookingDetails
     * @return true if book successful, else false
     */
    boolean book(Booking bookingDetails) throws Exception;

    /**
     * delete room by id
     * @param bookingId
     * @return true if successful delete, else false
     */
    boolean deleteBooking(String bookingId);

    /**
     * @return all booking list, if none return empty list
     */
    List<Booking> getBookingHistory();

    /**
     * get booking by id
     * @param bookingId
     * @return booking data, or null if not found
     */
    Booking getBookingById(String bookingId) throws Exception;


    //todo
    /**
     * update booking
     * @param booking
     * @return true if update successful, else false
     */
    boolean updateBooking(Booking booking) throws Exception;

    /**
     * get booking list by booking date
     * @param date
     * @return list of booking filtered by date, if non match return empty list
     */
    List<Booking> getBookingListByBookingDate(LocalDate date);

    /**
     * get occupied booking list based on start date and end date
     * @param startDate
     * @param endDate
     * @return
     */
    List<Booking> getOccupiedBookingListByDate(LocalDate startDate, LocalDate endDate);

}

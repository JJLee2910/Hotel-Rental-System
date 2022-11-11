package dao;

import data.Booking;
import util.DateUtils;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class BookingUtil {

    /**
     * Create instance variable
     */
    private static final String DATE_FORMATTER= "yyyy-MM-dd";
    private List<Booking> bookingList = new ArrayList<>();

    /**
     * Booking Util Constructor, will read the file and store the data in the bookingList when object is created
     */
    public BookingUtil() throws FileNotFoundException {
        readFile();
    }

    /**
     * Delete Booking
     * @param bookingId
     * @return true if booking is deleted, else throw exception
     */
    public boolean deleteBooking(String bookingId) throws Exception {
        for (Booking bookingDetails : bookingList) {
            if (bookingDetails.getBookingId().equals(bookingId)) {
                bookingList.remove(bookingDetails);
                updateBookingRecord(bookingList);
                return true;
            }
        }
        throw new Exception("Booking not found");
    }

    /**
     * Get All Booking
     * @return List of Booking
     */
    public List<Booking> getAllBookings() {
        return bookingList;
    }

    /**
     * Delete Booking
     * @param roomId room id
     * @param startDate start date
     * @param endDate end date
     * @return true if room is available, else throw exception
     */
    public boolean checkRoomIsAvailable(String roomId, LocalDate startDate, LocalDate endDate) throws Exception {
        for (Booking booking : bookingList) {
            if (booking.getRoomId().equals(roomId) && !startDate.isAfter(booking.getEndDate()) && !endDate.isBefore(booking.getStartDate())) {
                throw new Exception("Room is not available");
            }
        }
        return true;
    }

    /**
     * Update Booking Details in memory
     * @param bookingDetails new booking details
     * @return true if booking details updated successfully, else throw exception
     */
    public boolean updateBookingDetails(Booking bookingDetails) throws Exception {
        for (Booking booking : bookingList) {
            if (booking.getBookingId().equals(bookingDetails.getBookingId())) {
                booking.setBookingDate(bookingDetails.getBookingDate());
                booking.setStartDate(bookingDetails.getStartDate());
                booking.setEndDate(bookingDetails.getEndDate());
                booking.setCustomerName(bookingDetails.getCustomerName());
                booking.setCustomerIDNumber(bookingDetails.getCustomerIDNumber());
                booking.setCustomerContact(bookingDetails.getCustomerContact());
                booking.setCustomerEmail(bookingDetails.getCustomerEmail());
                booking.setRoomId(bookingDetails.getRoomId());
                booking.setDayOfStay(bookingDetails.getDayOfStay());

                booking.setRoomCharge((double) (bookingDetails.getDayOfStay() * 350));
                booking.setServiceTax((double) (bookingDetails.getDayOfStay() * 350) * 0.10);
                booking.setTourismTax((double) (bookingDetails.getDayOfStay() * 10));
                booking.setTotalCharge((double) (bookingDetails.getDayOfStay() * 350) + (double) (bookingDetails.getDayOfStay() * 350) * 0.10 + (double) (bookingDetails.getDayOfStay() * 10));

                updateBookingRecord(bookingList);
                return true;
            }
        }
        throw new Exception("Booking not found");
    }

    /**
     * Update Booking Details in file
     * @param bookingList List of Booking Detials
     */
    public void updateBookingRecord(List<Booking> bookingList) {
        DateTimeFormatter formatter = DateUtils.DATE_TIME_FORMATTER;
        try {
            clearTheFile();
            FileWriter myWriter = new FileWriter(Objects.requireNonNull(getClass().getResource("/database/booking.txt")).getFile(), true);
            BufferedWriter bufferWritter = new BufferedWriter(myWriter);
            PrintWriter printWriter = new PrintWriter(bufferWritter);

            for (Booking bookingDetails : bookingList) {

                bookingDetails.setRoomCharge((double) (bookingDetails.getDayOfStay() * 350));
                bookingDetails.setServiceTax((double) (bookingDetails.getDayOfStay() * 350) * 0.10);
                bookingDetails.setTourismTax((double) (bookingDetails.getDayOfStay() * 10));
                bookingDetails.setTotalCharge((double) (bookingDetails.getDayOfStay() * 350) + (double) (bookingDetails.getDayOfStay() * 350) * 0.10 + (double) (bookingDetails.getDayOfStay() * 10));

                printWriter.println(bookingDetails.getBookingId() + "," +bookingDetails.getBookingDate().format(formatter) + "," + bookingDetails.getStartDate().format(formatter) + "," +
                        bookingDetails.getEndDate().format(formatter) + "," + bookingDetails.getCustomerName() + "," +
                        bookingDetails.getCustomerIDNumber() + "," + bookingDetails.getCustomerContact() + "," +
                        bookingDetails.getCustomerEmail() + "," + bookingDetails.getRoomId() + "," +
                        bookingDetails.getDayOfStay() + "," + bookingDetails.getRoomCharge() + "," + bookingDetails.getServiceTax() + "," + bookingDetails.getTourismTax() + "," + bookingDetails.getTotalCharge());
            }

            printWriter.flush();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Get Booking Details based on Id
     * @param bookingId Booking Id
     * @return Booking Details based on Id if vaild, else throw exception
     */
    public Booking getBookingDetailsBasedOnId(String bookingId) throws Exception {
        for (Booking booking : bookingList) {
            if (booking.getBookingId().equals(bookingId)) {
                return booking;
            }
        }
        throw new Exception("Booking not found");
    }

    /**
     * Read Booking Details from file
     * Format:
     * BookingId, BookingDate, StartDate, EndDate, CustomerName, CustomerIDNumber, CustomerContact, CustomerEmail, RoomId, DayOfStay, RoomCharge, ServiceTax, TourismTax, TotalCharge
     */
    public void readFile() throws FileNotFoundException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        File myObj = new File(Objects.requireNonNull(getClass().getResource("/database/booking.txt")).getFile());
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (data.equals("")) {
                continue;
            }
            String[] bookingData = data.split(",");
            bookingList.add(new Booking(bookingData[0], LocalDate.parse(bookingData[1], formatter), LocalDate.parse(bookingData[2], formatter),
                    LocalDate.parse(bookingData[3], formatter), bookingData[4], bookingData[5], bookingData[6], bookingData[7], bookingData[8], parseInt(bookingData[9])));
        }
        myReader.close();
    }

    /**
     * Clear booking records in file
     */
    public static void clearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter(Objects.requireNonNull(BookingUtil.class.getResource("/database/booking.txt")).getFile(), false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }

    /**
     * Add booking details to file
     * @return Booking List
     */
    public boolean booking (Booking bookingDetails) throws Exception {
        if (checkRoomIsAvailable(bookingDetails.getRoomId(), bookingDetails.getStartDate(), bookingDetails.getEndDate())) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMATTER);
            FileWriter myWriter = null;
            BufferedWriter bufferWritter = null;
            PrintWriter printWriter = null;

            try {
                myWriter = new FileWriter(Objects.requireNonNull(getClass().getResource("/database/booking.txt")).getFile(), true);
                bufferWritter = new BufferedWriter(myWriter);
                printWriter = new PrintWriter(bufferWritter);

                printWriter.println(bookingDetails.getBookingId() + "," +bookingDetails.getBookingDate().format(formatter) + "," + bookingDetails.getStartDate().format(formatter) + "," +
                        bookingDetails.getEndDate().format(formatter) + "," + bookingDetails.getCustomerName() + "," +
                        bookingDetails.getCustomerIDNumber() + "," + bookingDetails.getCustomerContact() + "," +
                        bookingDetails.getCustomerEmail() + "," + bookingDetails.getRoomId() + "," +
                        bookingDetails.getDayOfStay() + "," + bookingDetails.getRoomCharge() + "," + bookingDetails.getServiceTax() + "," + bookingDetails.getTourismTax() + "," + bookingDetails.getTotalCharge());
                printWriter.flush();

                return true;
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            } finally {
                try {
                    assert bufferWritter != null;
                    assert printWriter != null;
                    bufferWritter.close();
                    printWriter.close();
                    myWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        throw new Exception("Error Occurred");
    }

    public List<Booking> getOccupiedBookingListByDate(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookingList = new ArrayList<>();
        for (Booking booking : this.bookingList) {
            if (booking.getEndDate().isBefore(startDate)) continue;
            if (booking.getStartDate().isAfter(endDate)) continue;
            bookingList.add(booking);
        }
        return bookingList;
    }
}

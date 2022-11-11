package data;

import java.time.LocalDate;
import java.util.UUID;

public class Booking {
    private String bookingId;
    private LocalDate bookingDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String customerName;
    private String customerIDNumber;
    private String customerContact;
    private String customerEmail;
    private String roomId;
    private Integer dayOfStay;
    private Double totalCharge;
    private Double roomCharge;
    private Double serviceTax;
    private Double tourismTax;

    public Booking(LocalDate bookingDate, LocalDate startDate, LocalDate endDate, String customerName, String customerIDNumber, String customerContact, String customerEmail, String roomId, Integer dayOfStay) {
        this.bookingId = UUID.randomUUID().toString();
        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerName = customerName;
        this.customerIDNumber = customerIDNumber;
        this.customerContact = customerContact;
        this.customerEmail = customerEmail;
        this.roomId = roomId;
        this.dayOfStay = dayOfStay;
        this.roomCharge = (double) (dayOfStay * 350);
        this.serviceTax = (double) (dayOfStay * 350) * 0.10;
        this.tourismTax = (double) (dayOfStay * 10);
        this.totalCharge = (double) (dayOfStay * 350) + (double) (dayOfStay * 350) * 0.10 + (double) (dayOfStay * 10);
    }

    public Booking(String bookingId, LocalDate bookingDate, LocalDate startDate, LocalDate endDate, String customerName, String customerIDNumber, String customerContact, String customerEmail, String roomId, Integer dayOfStay) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerName = customerName;
        this.customerIDNumber = customerIDNumber;
        this.customerContact = customerContact;
        this.customerEmail = customerEmail;
        this.roomId = roomId;
        this.dayOfStay = dayOfStay;
        this.roomCharge = (double) (dayOfStay * 350);
        this.serviceTax = (double) (dayOfStay * 350) * 0.10;
        this.tourismTax = (double) (dayOfStay * 10);
        this.totalCharge = (double) (dayOfStay * 350) + (double) (dayOfStay * 350) * 0.10 + (double) (dayOfStay * 10);
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Double getRoomCharge() {
        return roomCharge;
    }

    public void setRoomCharge(Double roomCharge) {
        this.roomCharge = roomCharge;
    }

    public Double getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(Double serviceTax) {
        this.serviceTax = serviceTax;
    }

    public Double getTourismTax() {
        return tourismTax;
    }

    public void setTourismTax(Double tourismTax) {
        this.tourismTax = tourismTax;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerIDNumber() {
        return customerIDNumber;
    }

    public void setCustomerIDNumber(String customerIDNumber) {
        this.customerIDNumber = customerIDNumber;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Integer getDayOfStay() {
        return dayOfStay;
    }

    public void setDayOfStay(Integer dayOfStay) {
        this.dayOfStay = dayOfStay;
    }
}

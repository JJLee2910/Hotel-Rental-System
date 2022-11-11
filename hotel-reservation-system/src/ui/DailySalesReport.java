package ui;

import data.Booking;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import service.BookingService;
import system.HotelReservationSystem;
import util.DateUtils;
import util.PaymentUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Form used for daily sales
 */
public class DailySalesReport extends BaseForm {
    private JPanel panel;
    private JButton backButton;
    private JTextField roomBooked;
    private JTextField totalSales;
    private JDatePickerImpl datePicker;

    private final BookingService bookingService;

    public DailySalesReport(BookingService bookingService, HotelReservationSystem hotelReservationSystem) {
        super(hotelReservationSystem);
        this.bookingService = bookingService;

        // set text field to be unmodifiable
        roomBooked.setEnabled(false);
        totalSales.setEnabled(false);

        // set add button listener
        backButton.addActionListener(this::toMainMenu);
        datePicker.addActionListener(this::generateSalesByDate);
    }

    /**
     * if date is not selected, clear all data.
     * else get all booking on the selected date, generate room booked by that day and total sales
     * @param event unused param
     */
    private void generateSalesByDate(ActionEvent event) {
        LocalDate date = DateUtils.getDateFromDatePicker(datePicker);
        if (date == null) {
            clearAllData();
            return;
        }

        List<Booking> bookingList = bookingService.getBookingListByBookingDate(date);

        Double totalChargePerDay = bookingList.stream()
                .map(Booking::getTotalCharge)
                .reduce(0d, Double::sum);

        roomBooked.setText(String.valueOf(bookingList.size()));
        totalSales.setText(PaymentUtils.getPaymentFormat(totalChargePerDay));
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    protected void clearAllData() {
        roomBooked.setText("");
        totalSales.setText("");
        datePicker.getModel().setValue(null);
    }

    /**
     * create date picker
     */
    private void createUIComponents() {
        DateModel<Date> model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
    }
}

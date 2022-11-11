package ui;

import data.Room;
import data.nulldata.NullRoom;
import org.jdatepicker.DateModel;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import service.BookingService;
import service.RoomService;
import system.HotelReservationSystem;
import util.DateUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Form used to booking
 */
public class Booking extends BaseForm {
    private JButton bookButton;
    private JButton cancelButton;
    private JTextField customerName;
    private JTextField customerID;
    private JTextField customerContact;
    private JTextField customerEmail;
    private JComboBox<Room> roomComboBox;
    private JPanel panel;
    private JTextField days;
    private JDatePickerImpl datePicker;
    private JButton searchRoomButton;

    private final BookingService bookingService;
    private final RoomService roomService;

    public Booking(RoomService roomService, BookingService bookingService, HotelReservationSystem hotelReservationSystem) {
        super(hotelReservationSystem);
        this.bookingService = bookingService;
        this.roomService = roomService;

        // init of room list
        roomComboBox.addItem(new NullRoom());

        // add button listener
        searchRoomButton.addActionListener(this::filterRoomList);
        cancelButton.addActionListener(this::toMainMenu);
        bookButton.addActionListener(this::bookRoom);
    }

    /**
     * validate date
     */
    private void validateSearchCriteria() throws Exception {
        LocalDate startDateTime = DateUtils.getDateFromDatePicker(datePicker);
        if (startDateTime == null) throw new Exception("No date selected");

        try {
            int day = Integer.parseInt(days.getText());
            if (day < 1 || day > 7) throw new Exception();
        } catch (Exception e) {
            throw new Exception("days must be within 1 - 7");
        }
    }

    /**
     * book room.
     * 1. validate data first.
     * 2. convert form data into booking object
     * 3. try to book the room
     * 4. open receipt
     * 5. clear all data
     *
     * if any step throws an exception, will prompt error
     * @param ignore
     */
    public void bookRoom(ActionEvent ignore) {
        try {
            // validate data
            validateData();

            // convert form data into booking object
            LocalDate startDateTime = DateUtils.getDateFromDatePicker(datePicker);
            assert startDateTime != null;
            data.Booking data = new data.Booking(
                    LocalDate.now(),
                    startDateTime,
                    startDateTime.plusDays(Long.parseLong(days.getText()) - 1),
                        customerName.getText(),
                    customerID.getText(),
                    customerContact.getText(),
                    customerEmail.getText(),
                    ((Room) Objects.requireNonNull(roomComboBox.getSelectedItem())).getId(),
                    Integer.parseInt(days.getText())
            );

            // book room
            bookingService.book(data);

            // show receipt
            app.openReceipt(bookingService.getBookingById(data.getBookingId()));

            // clear all data
            clearAllData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * if start date // room id filter is used, the function will be called.
     * match all booking with start date and room id, filter out all unmatched booking
     * might leave empty room list in the end
     * @param event
     */
    private void filterRoomList(ActionEvent event) {
        roomComboBox.removeAllItems();
        List<Room> roomList = roomService.getAllRooms();

        try {
            validateSearchCriteria();
            int day = Integer.parseInt(days.getText());
            LocalDate filterStartDate = DateUtils.getDateFromDatePicker(datePicker);

            if (filterStartDate == null) {
                throw new Exception();
            }

            List<data.Booking> bookingList = bookingService.getOccupiedBookingListByDate(filterStartDate, filterStartDate.plusDays(day));

            List<String> occupiedRoomIdList = bookingList.stream()
                    .map(data.Booking::getRoomId)
                    .collect(Collectors.toList());

            roomList = roomList.stream()
                    .filter(room -> !occupiedRoomIdList.contains(room.getId()))
                    .collect(Collectors.toList());



            roomList.forEach(roomComboBox::addItem);


        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * validate data, if data is not valid, throw an exception
     * @throws Exception with error message
     */
    private void validateData() throws Exception {

        validateSearchCriteria();

        if (customerName.getText().isEmpty()) throw new Exception("Invalid name");

        if (customerContact.getText().isEmpty()) throw new Exception("Invalid contact");

        if (customerID.getText().isEmpty()) throw new Exception("Invalid Id");

        if (customerEmail.getText().isEmpty()) throw new Exception("Invalid email");
    }

    @Override
    protected void clearAllData() {
        customerName.setText("");
        customerID.setText("");
        customerContact.setText("");
        customerEmail.setText("");
        roomComboBox.setSelectedIndex(0);
        days.setText("");
        datePicker.getModel().setValue(null);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    /**
     * function to create external library date picker
     */
    private void createUIComponents() {
        DateModel<Date> model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
    }
}

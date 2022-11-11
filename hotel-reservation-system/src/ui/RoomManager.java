package ui;

import data.Booking;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * form for room manager
 */
public class RoomManager extends BaseForm {
    private JComboBox<Room> roomList;
    private JTextField customerName;
    private JTextField customerID;
    private JTextField customerContact;
    private JTextField customerEmail;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton backButton;
    private JPanel panel;
    private JComboBox<String> bookingIdList;
    private JTextField startDate;
    private JTextField endDate;
    private JTextField roomId;
    private JDatePickerImpl datePicker;
    private JButton viewReceiptButton;
    private final BookingService bookingService;
    private final RoomService roomService;

    public RoomManager(BookingService bookingService, RoomService roomService, HotelReservationSystem hotelReservationSystem) {
        super(hotelReservationSystem);
        this.bookingService = bookingService;
        this.roomService = roomService;

        // generate room list
        initRoomList();

        // generate booking id list
        refreshBookingList();

        // add button listener
        bookingIdList.addActionListener(this::populateBookingDetails);
        backButton.addActionListener(this::toMainMenu);
        datePicker.addActionListener(this::filterBookingList);
        roomList.addActionListener(this::filterBookingList);
        deleteButton.addActionListener(this::deleteSelectedBooking);
        updateButton.addActionListener(this::updateSelectedBooking);
        viewReceiptButton.addActionListener(this::openReceipt);

        // set text field to unmodifiable
        roomId.setEnabled(false);
        startDate.setEnabled(false);
        endDate.setEnabled(false);
    }

    /**
     * set booking id list to null, then load again
     */
    private void refreshBookingList() {
        bookingIdList.removeAllItems();
        bookingIdList.addItem("");
        bookingService.getBookingHistory().forEach(booking -> bookingIdList.addItem(booking.getBookingId()));
    }

    /**
     * if no booking id selected, show error.
     * Else open receipt
     * @param event
     */
    private void openReceipt(ActionEvent event) {
        String selectedId = (String) bookingIdList.getSelectedItem();
        if (selectedId == null || selectedId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "no booking selected", "Message", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            app.openReceipt(bookingService.getBookingById(selectedId));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * update customer info for selected booking.
     * show error if update fail or no booking selected
     * @param event
     */
    private void updateSelectedBooking(ActionEvent event) {
        String selectedId = (String) bookingIdList.getSelectedItem();
        if (selectedId == null || selectedId.isEmpty()) {
            JOptionPane.showMessageDialog(null, "update fail", "Message", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Booking booking = bookingService.getBookingById(selectedId);
            booking.setCustomerEmail(customerEmail.getText());
            booking.setCustomerName(customerName.getText());
            booking.setCustomerIDNumber(customerID.getText());
            booking.setCustomerContact(customerContact.getText());
            if (bookingService.updateBooking(booking)) {
                JOptionPane.showMessageDialog(null, "update successful", "Message", JOptionPane.INFORMATION_MESSAGE);
                refreshBookingList();
                clearAllData();
            } else {
                JOptionPane.showMessageDialog(null, "update fail", "Message", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * delete selected booking
     * show error if delete fail or no booking selected
     * refresh booking id list right after delete
     * @param event
     */
    private void deleteSelectedBooking(ActionEvent event) {
        if (bookingService.deleteBooking((String) bookingIdList.getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "delete successful", "Message", JOptionPane.INFORMATION_MESSAGE);
            refreshBookingList();
            clearAllData();
            } else {
                JOptionPane.showMessageDialog(null, "delete fail", "Message", JOptionPane.ERROR_MESSAGE);
            }
    }

    /**
     * if selected item is null, clear all text
     * else populate data by selected booking
     * @param event
     */
    private void populateBookingDetails(ActionEvent event) {
        try {
            String selectedId = (String) bookingIdList.getSelectedItem();
            if (Objects.equals(selectedId, "") || selectedId == null) {
                clearAllText();
                return;
            }

            Booking data = bookingService.getBookingById(selectedId);
            roomId.setText(data.getRoomId());
            customerName.setText(data.getCustomerName());
            customerContact.setText(data.getCustomerContact());
            customerEmail.setText(data.getCustomerEmail());
            customerID.setText(data.getCustomerIDNumber());
            startDate.setText(data.getStartDate().toString());
            endDate.setText(data.getEndDate().toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void clearAllText() {
        roomId.setText("");
        customerName.setText("");
        customerContact.setText("");
        customerEmail.setText("");
        customerID.setText("");
        startDate.setText("");
        endDate.setText("");
    }

    /**
     * create date picker
     */
    private void createUIComponents() {
        DateModel<Date> model = new UtilDateModel();
        JDatePanelImpl datePanel = new JDatePanelImpl(model, new Properties());
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
    }

    /**
     * populate room list, with one null selection
     */
    private void initRoomList() {
        roomList.addItem(new NullRoom());
        roomService.getAllRooms().forEach(roomList::addItem);
    }

    /**
     * if start date // room id filter is used, the function will be called.
     * match all booking with start date and room id, filter out all unmatched booking
     * might leave empty room list in the end
     * @param event
     */
    private void filterBookingList(ActionEvent event) {
        bookingIdList.removeAllItems();

        Room selectedRoom = (Room) roomList.getSelectedItem();
        LocalDate filterStartDate = DateUtils.getDateFromDatePicker(datePicker);

        List<data.Booking> bookingList = bookingService.getBookingHistory();

        List<String> filteredBookingIdList = bookingList.stream()
                .filter(booking -> bookingMatchRoom(booking, selectedRoom))
                .filter(booking -> bookingMatchStartDate(booking, filterStartDate))
                .map(Booking::getBookingId)
                .collect(Collectors.toList());

        if (filteredBookingIdList.size() == bookingList.size()) {
            bookingIdList.addItem("");
        }

        filteredBookingIdList.forEach(bookingIdList::addItem);
    }

    /**
     * function to match booking with start date
     * @param booking
     * @param filterStartDate
     * @return
     */
    private boolean bookingMatchStartDate(Booking booking, LocalDate filterStartDate) {
        if (filterStartDate == null) return true;

        return booking.getStartDate().equals(filterStartDate);
    }

    /**
     * function to match booking with room id
     * @param booking
     * @param selectedRoom
     * @return
     */
    private boolean bookingMatchRoom(Booking booking, Room selectedRoom) {
        if (selectedRoom == null || selectedRoom.getClass().equals(NullRoom.class)) return true;

        return booking.getRoomId().equals(selectedRoom.getId());
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    protected void clearAllData() {
        datePicker.getModel().setValue(null);
        roomList.setSelectedIndex(0);
        bookingIdList.setSelectedIndex(0);
    }

    @Override
    public void open() {
        super.open();
        refreshBookingList();
    }
}

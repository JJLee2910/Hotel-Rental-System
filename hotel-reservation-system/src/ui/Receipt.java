package ui;

import data.Booking;
import data.Room;
import service.RoomService;
import system.HotelReservationSystem;
import util.DateUtils;
import util.PaymentUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * form for receipt
 */
public class Receipt extends BaseForm {
    private JTextField roomId;
    private JTextField roomType;
    private JTextField customerName;
    private JTextField customerId;
    private JTextField customerContact;
    private JTextField customerEmail;
    private JTextField price;
    private JTextField serviceTax;
    private JTextField tourismTax;
    private JTextField total;
    private JButton backButton;
    private JPanel panel;
    private JTextField startDate;
    private JTextField days;

    private final RoomService roomService;

    public Receipt(RoomService roomService, HotelReservationSystem app) {
        super(app);
        this.roomService = roomService;

        // set all data as unmodifiable
        roomId.setEnabled(false);
        roomType.setEnabled(false);
        startDate.setEnabled(false);
        days.setEnabled(false);
        customerName.setEnabled(false);
        customerId.setEnabled(false);
        customerContact.setEnabled(false);
        customerEmail.setEnabled(false);
        price.setEnabled(false);
        serviceTax.setEnabled(false);
        tourismTax.setEnabled(false);
        total.setEnabled(false);

        // add button listener
        backButton.addActionListener(this::close);
    }

    /**
     * since receipt is pop up windows, need special handle for close function.
     * will close from app, since only app contains the JFrame
     * @param event
     */
    private void close(ActionEvent event) {
        this.panel.setVisible(false);
        app.closeReceipt();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    protected void clearAllData() {
        roomId.setText("");
        roomType.setText("");
        startDate.setText("");
        days.setText("");
        customerName.setText("");
        customerId.setText("");
        customerContact.setText("");
        customerEmail.setText("");
        price.setText("");
        serviceTax.setText("");
        tourismTax.setText("");
        total.setText("");
    }

    /**
     * every opening of receipt must parse in a booking object as data.
     * @param data
     */
    public void open(Booking data) throws Exception {
        super.open();
        initData(data);
    }

    /**
     * logic to init data
     * @param data
     */
    public void initData(Booking data) throws Exception {
        try {
            Room room = roomService.getRoomById(data.getRoomId());

            roomId.setText(data.getRoomId());
            roomType.setText(room.getView().toString());
            startDate.setText(data.getStartDate().format(DateUtils.DATE_TIME_FORMATTER));
            days.setText(data.getDayOfStay().toString());

            customerName.setText(data.getCustomerName());
            customerId.setText(data.getCustomerIDNumber());
            customerContact.setText(data.getCustomerContact());
            customerEmail.setText(data.getCustomerEmail());

            price.setText(PaymentUtils.getPaymentFormat(data.getRoomCharge()));
            serviceTax.setText(PaymentUtils.getPaymentFormat(data.getServiceTax()));
            tourismTax.setText(PaymentUtils.getPaymentFormat(data.getTourismTax()));
            total.setText(PaymentUtils.getPaymentFormat(data.getTotalCharge()));
        } catch (Exception e) {
            throw new Exception("cannot open receipt");
        }

    }
}

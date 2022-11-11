package ui;

import system.HotelReservationSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * form for main menu
 */
public class MainMenu extends BaseForm {
    private JButton bookingButton;
    private JButton dailySalesReportButton;
    private JPanel panel1;
    private JButton manageRoomButton;
    private JButton logOutButton;

    public MainMenu(HotelReservationSystem hotelReservationSystem) {
        super(hotelReservationSystem);

        // add button listener
        bookingButton.addActionListener(this::toBooking);
        dailySalesReportButton.addActionListener(this::toDailySalesReport);
        manageRoomButton.addActionListener(this::toRoomManager);
        logOutButton.addActionListener(this::toLogin);
    }

    /**
     * null function since current page is main menu
     * @param actionEvent unused
     */
    @Override
    protected void toMainMenu(ActionEvent actionEvent) {}

    /**
     * button route to booking page
     * @param ignore
     */
    public void toBooking(ActionEvent ignore) {
        this.close();
        app.toBooking();
    }

    /**
     * button route to room manager page
     * @param ignore
     */
    public void toRoomManager(ActionEvent ignore) {
        this.close();
        app.toRoomManager();
    }

    /**
     * button route to login page
     * @param ignore
     */
    public void toLogin(ActionEvent ignore) {
        this.close();
        app.toLogin();
    }

    /**
     * button route to daile sale report page
     * @param ignore
     */
    public void toDailySalesReport(ActionEvent ignore) {
        this.close();
        app.toDailySalesReport();
    }

        public JPanel getPanel() {
            return panel1;
        }

    /**
     * no data to clear
     */
    @Override
    protected void clearAllData() {}
}

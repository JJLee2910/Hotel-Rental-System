package system;

import service.BookingService;
import service.LogInService;
import service.RoomService;
import service.implementation.BookingServiceImpl;
import service.implementation.LogInServiceImpl;
import service.implementation.RoomServiceImpl;
import ui.*;

import javax.swing.*;

public class HotelReservationSystem {
    // services
    private final LogInService logInService;
    private final RoomService roomService;
    private final BookingService bookingService;

    // UI
    private final Login login;
    private final MainMenu mainMenu;
    private final Booking booking;
    private final RoomManager roomManager;
    private final Receipt receipt;
    private final DailySalesReport dailySalesReport;

    // frame
    private final JFrame jFrame;
    private final JFrame receiptFrame;

    public HotelReservationSystem() {
        // define backend logic
        logInService = new LogInServiceImpl();
        roomService = new RoomServiceImpl();
        bookingService = new BookingServiceImpl();

        // define frames
        jFrame = new JFrame("Resort Room Booking System");
        receiptFrame = new JFrame("Receipt");

        // define ui
        login = new Login(logInService, this);
        mainMenu = new MainMenu(this);
        booking = new Booking(roomService, bookingService, this);
        roomManager = new RoomManager(bookingService, roomService, this);
        dailySalesReport = new DailySalesReport(bookingService,this);
        receipt = new Receipt(roomService,this);

        // main frame settings
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(800,600);

        // receipt frame settings
        receiptFrame.setContentPane(receipt.getPanel());
        receiptFrame.setSize(600, 700);
        receiptFrame.setVisible(false);

        // init of system
        toLogin();
    }

    public static void main(String[] args) {
        new HotelReservationSystem();
    }

    /**
     * logic for redirecting to main menu
     */
    public void toMainMenu() {
        jFrame.setContentPane(mainMenu.getPanel());
        mainMenu.open();
        jFrame.setVisible(true);
    }

    /**
     * logic for redirecting to login
     */
    public void toLogin() {
        jFrame.setContentPane(login.getPanel());
        login.open();
        jFrame.setVisible(true);
    }

    /**
     * logic for redirecting to booking
     */
    public void toBooking() {
        jFrame.setContentPane(booking.getPanel());
        booking.open();
        jFrame.setVisible(true);
    }

    /**
     * logic for redirecting to room manager
     */
    public void toRoomManager() {
        jFrame.setContentPane(roomManager.getPanel());
        roomManager.open();
        jFrame.setVisible(true);
    }

    /**
     * logic for redirecting to daily sales report
     */
    public void toDailySalesReport() {
        jFrame.setContentPane(dailySalesReport.getPanel());
        dailySalesReport.open();
        jFrame.setVisible(true);
    }

    /**
     * logic for open receipt
     */
    public void openReceipt(data.Booking booking) throws Exception {
        try {
            receipt.open(booking);
        } catch (Exception e) {
            e.printStackTrace();
        }
        receiptFrame.setFocusable(true);
        receiptFrame.setVisible(true);
        receiptFrame.setAlwaysOnTop(true);
    }

    /**
     * logic for close receipt
     */
    public void closeReceipt() {
        receipt.close();
        receiptFrame.setFocusable(false);
        receiptFrame.setVisible(false);
        receiptFrame.setAlwaysOnTop(false);
    }
}

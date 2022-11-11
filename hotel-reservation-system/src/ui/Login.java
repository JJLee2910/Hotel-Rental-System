package ui;

import service.LogInService;
import system.HotelReservationSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * form to log in
 */
public class Login extends BaseForm {
    private JTextField username;
    private JPanel panel1;
    private JPasswordField password;
    private JButton loginButton;
    private final LogInService logInService;

    public Login(LogInService logInService, HotelReservationSystem hotelReservationSystem) {
        super(hotelReservationSystem);
        this.logInService = logInService;

        // add button listener
        loginButton.addActionListener(this::login);
    }

    /**
     * if login successful, show login successful page, then route to main menu.
     * else show error message
     * @param ignore
     */
    private void login(ActionEvent ignore) {
        try {
            if (logInService.login(username.getText(), new String(password.getPassword()))) {
                JOptionPane.showMessageDialog(null, "login successful!", "Message", JOptionPane.INFORMATION_MESSAGE);
                close();
                app.toMainMenu();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid Username or Password!", "Message", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Message", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    protected void clearAllData() {
        username.setText("");
        password.setText("");
    }

    public JPanel getPanel() {
        return panel1;
    }
}

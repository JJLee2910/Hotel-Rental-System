package ui;

import system.HotelReservationSystem;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Base class for UI.
 */
public abstract class BaseForm {
    protected final HotelReservationSystem app;
    /**
     * the keyword protected are access modifier used for attributes
     * can be used within a same class or subclasses of same packages
    */
    protected BaseForm(HotelReservationSystem app) {
        this.app = app;
    }

    /**
     * Each UI must at least have one JPanel
     * @return The Base JPanel of the UI
     */
    public abstract JPanel getPanel();

    /**
     * Clear all data within the form. Used to renew the form.
     */
    protected abstract void clearAllData();

    /**
     * set the base panel as visible
     */
    public void open() {
        clearAllData();
        this.getPanel().setVisible(true);
    }

    /**
     * set the base panel as invisible
     */
    public void close() {
        this.getPanel().setVisible(false);
    }

    /**
     * close current UI, then redirect to main menu
     * @param actionEvent unused
     */
    protected void toMainMenu(ActionEvent actionEvent) {
        this.close();
        close();
        app.toMainMenu();
    }
}

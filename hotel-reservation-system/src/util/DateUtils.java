package util;

import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * class for common date function and data
 */
public class DateUtils {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * get selected date from date picker
     * @param datePicker
     * @return LocalDate data
     */
    public static LocalDate getDateFromDatePicker(JDatePicker datePicker) {
        DateModel model = datePicker.getModel();

        if (model.getValue() == null) {
            return null;
        }

        String day = String.format("%02d", model.getDay());
        String month = String.format("%02d", model.getMonth() + 1);
        String year = String.format("%04d", model.getYear());
        return LocalDate.parse(year + "-" + month + "-" + day, DATE_TIME_FORMATTER);
    }
}

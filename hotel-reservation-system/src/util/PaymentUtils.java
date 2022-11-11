package util;

/**
 * common function for payments
 */
public class PaymentUtils {

    /**
     * convert double data to RM string
     * eg. 1.0 -> RM 1.00
     * @param data
     * @return
     */
    public static String getPaymentFormat(double data) {
        return String.format("RM %.2f", data);
    }
}

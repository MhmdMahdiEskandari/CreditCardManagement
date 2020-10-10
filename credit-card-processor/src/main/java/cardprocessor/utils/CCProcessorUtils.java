/**
 *
 */
package cardprocessor.utils;

import cardprocessor.constants.CreditCardDataConstants;
import cardprocessor.model.CreditCardData;

/**
 * @author  MhmdMahdi
 * @project Credit-Card-Processor
 */

public class CCProcessorUtils {

    public static boolean validateCardDetails(CreditCardData creditCardData) {
        boolean cardResult = true;

        try {
            if (creditCardData.getCardNumber().length() > 19 || Long.parseLong(creditCardData.getCardNumber()) < 0) {
                cardResult = false;
            }
        } catch (NumberFormatException e) {
            cardResult = false;
        }

        return cardResult;
    }

    public static String formatAmount(String amount) {
        String formattedAmount = null;
        amount = String.format("%.2f", Double.parseDouble(amount));
        if (Double.parseDouble(amount) >= 0) {
            formattedAmount = CreditCardDataConstants.POUND + amount;
        } else {
            formattedAmount = CreditCardDataConstants.NEGATIVE_POUND + String.format("%.2f", Math.abs(Double.parseDouble(amount)));
        }

        return formattedAmount;
    }

}

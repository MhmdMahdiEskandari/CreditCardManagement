/**
 *
 */
package cardprocessor.model;

/**
 * @author  MhmdMahdi
 * @project Credit-Card-Processor
 */
public class CreditCardData {

    private String cardNumber;

    private String cvv2;

    private String expDate;

    private String pin;

    private String firstName;

    private String lastName;

    private String totalCredit;

    public CreditCardData() {
        this.cardNumber = "";
        this.cvv2 = "";
        this.expDate = "";
        this.pin = "";
        this.firstName = "";
        this.lastName = "";

    }

    public CreditCardData(CCTransactionRequestModel requestModel) {
        this.cardNumber = requestModel.getSource();
        this.cvv2 = requestModel.getCvv2();
        this.expDate = requestModel.getExpDate();
        this.pin = requestModel.getPin();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}

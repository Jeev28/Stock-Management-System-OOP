import java.time.LocalDate;
public class CreditCard implements PaymentMethod{

    private final int cardNum;
    private final int securityNum;

    public CreditCard(int cardNum, int securityNum) {
        this.cardNum = cardNum;
        this.securityNum = securityNum;
    }

    public int getSecurityNum() {
        return securityNum;
    }

    public int getCardNum() {
        return cardNum;
    }

    //This uses the payment interface and creates a receipt for the credit card payment
    @Override
    public Receipt processPayment(double amount, Address fullAddress) {
        return new Receipt(amount + " paid by Credit Card using " + getCardNum() + " on " +
                            LocalDate.now() + ", and the delivery address is " + fullAddress);
    }


}


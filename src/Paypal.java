import java.time.LocalDate;
public class Paypal implements PaymentMethod{

    private final String email;

    public Paypal(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
    
    //create receipt for paypal payment
    @Override
    public Receipt processPayment(double amount, Address fullAddress) {
        return new Receipt(amount + " paid by PayPal using " + getEmail() + " on " + LocalDate.now()
                            + ", and the delivery address is " + fullAddress);
    }

}

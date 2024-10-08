public class Receipt {

    private final String receiptString;

    public Receipt(String receiptString) {
        this.receiptString = receiptString;
    }

    @Override
    public String toString() {
        return receiptString;
    }
}


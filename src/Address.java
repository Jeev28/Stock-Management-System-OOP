public class Address {

    private int houseNum;
    private String postcode;
    private String city;

    public Address(int houseNum, String postcode, String city) {
        this.houseNum = houseNum;
        this.postcode = postcode;
        this.city = city;
    }

    //This create a String of the user's address
    @Override
    public String toString() {
        return houseNum + ", " + city + ", " + postcode;
    }
}

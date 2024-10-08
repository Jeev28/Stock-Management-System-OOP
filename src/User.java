import java.util.ArrayList;

public abstract class User {

    private final int userId;
    private final String username;
    private final String name;
    private final Address address;
    private final Role role;


    public User(int userId, String username, String name, Address address, Role role) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.address = address;
        this.role = role;
    }

    public abstract ArrayList<String> viewAllProducts(ArrayList<Product> products);

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public Address getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public abstract String toString();

}

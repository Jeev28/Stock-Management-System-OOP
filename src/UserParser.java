import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//for each line in the user file, it creates a user object (customer or admin)

public class UserParser {
    public static ArrayList<User> parseUsers(String filename, ArrayList<Product> products) throws IOException {
        ArrayList<User> users = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(", ");
            int userId = Integer.parseInt(fields[0]);
            String username = fields[1];
            String name = fields[2];
            int houseNum = Integer.parseInt(fields[3]);
            String postcode = fields[4];
            String city = fields[5];
            Role role = Role.valueOf(fields[6].toUpperCase());

            Address address = new Address(houseNum, postcode, city);
            switch (role) {
                case ADMIN:
                    users.add(new Admin(userId, username, name, address, products));
                    break;
                case CUSTOMER:
                    users.add(new Customer(userId, username, name, address));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown user role: " + role);
            }
        }

        reader.close();
        return users;
    }
}


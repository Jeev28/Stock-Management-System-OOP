import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//Reads from file and for each line it splits it and assigns each split part to a property.
//using them, it makes a product (mouse or keyboard) object for each line

public class ProductParser {
    public static ArrayList<Product> parseProducts(String filename) throws IOException {
        ArrayList<Product> products = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] fields = line.split(", ");
            int barcode = Integer.parseInt(fields[0]);
            String productType = fields[1].toLowerCase();
            String typeDetail = fields[2].toLowerCase();
            String brand = fields[3];
            String color = fields[4];
            ConnectivityType connectivity = ConnectivityType.valueOf(fields[5].toUpperCase());
            int quantityInStock = Integer.parseInt(fields[6]);
            double originalCost = Double.parseDouble(fields[7]);
            double retailPrice = Double.parseDouble(fields[8]);
            ProductCategory category = ProductCategory.valueOf(productType.toUpperCase());

            switch (productType) {
                case "keyboard":
                    KeyboardLayout layout = KeyboardLayout.valueOf(fields[9].toUpperCase());
                    KeyboardType keyboardType = KeyboardType.valueOf(typeDetail.toUpperCase());
                    products.add(new Keyboard(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, category, layout, keyboardType));
                    break;
                case "mouse":
                    MouseType mouseType = MouseType.valueOf(typeDetail.toUpperCase());
                    int numOfButtons = Integer.parseInt(fields[9]);
                    products.add(new Mouse(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, category, mouseType, numOfButtons));
                    break;
                default:
                    throw new IllegalArgumentException("Unknown product type: " + productType);
            }
        }

        reader.close();
        return products;
    }
}

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Admin extends User {
	private ArrayList<Product> products;

    public Admin(int userId, String username, String name, Address address, ArrayList<Product> products) {
        super(userId, username, name, address, Role.ADMIN);
        this.products = products;
    }

    
    //This sorts all products by retail price and creates an array of the String version of all products
    @Override
    public ArrayList<String> viewAllProducts(ArrayList<Product> products) {
        Collections.sort(products, Comparator.comparing(Product::getRetailPrice));

        ArrayList<String> sortedProductsToPrint = new ArrayList<>();
        for (Product product: products) {
            sortedProductsToPrint.add(product.toString());
        }

        return sortedProductsToPrint;
    }
    
    
    //This adds keyboard product to stock text file - first checks for errors (empty fields, barcode already used)
    public void addKeyboardStock(int barcode, String brand, String color, ConnectivityType connectivity, int quantityInStock, double originalCost, double retailPrice, KeyboardLayout layout, KeyboardType keyboardType) throws IOException, Exception {
    	//check barcode doesnt already exist 
    	for (Product product : products) {
            if (product.getBarcode() == barcode) {
                throw new Exception("Product with barcode " + barcode + " already exists in stock.");
            }
        }
    	
        if (brand.isEmpty() || color.isEmpty() || connectivity == null || layout == null || keyboardType == null) {
            throw new Exception("All fields are required.");
        }

        if (barcode < 0 || quantityInStock < 0 || originalCost < 0 || retailPrice < 0) {
            throw new Exception("Number fields cannot be negative.");
        }
    	
    	Keyboard keyboard = new Keyboard(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, ProductCategory.KEYBOARD, layout, keyboardType);
        products.add(keyboard);
        
        //add new line and convert to lowercase before writing to file 
        String keyboardDetails = String.format("\n%d, keyboard, %s, %s, %s, %s, %d, %.2f, %.2f, %s", 
                barcode, 
                keyboardType.toString().toLowerCase(), 
                brand.toLowerCase(), 
                color.toLowerCase(), 
                connectivity.toString().toLowerCase(), 
                quantityInStock, 
                originalCost, 
                retailPrice, 
                layout.toString().toLowerCase()
            );

            // Write to file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Stock.txt", true))) {
                writer.write(keyboardDetails);
            }
    }
    
    //this adds mouse stock 
    public void addMouseStock(int barcode, String brand, String colour, ConnectivityType connectivity, int quantity, double originalCost, double retailPrice, MouseType type, int numOfButtons) throws IOException, Exception {
	   //check barcode doesnt already exist 
    	for (Product product : products) {
           if (product.getBarcode() == barcode) {
               throw new Exception("Product with barcode " + barcode + " already exists in stock.");
           }
       }
    	
    	   if (brand.isEmpty() || colour.isEmpty() || connectivity == null || type == null) {
               throw new Exception("All fields are required.");
           }

           if (barcode < 0 || quantity < 0 || originalCost < 0 || retailPrice < 0 || numOfButtons < 0) {
               throw new IllegalArgumentException("Number fields cannot be negative.");
           }
       	
    	Mouse mouse = new Mouse(barcode, brand, colour, connectivity, quantity, originalCost, retailPrice, ProductCategory.MOUSE, type, numOfButtons);
        products.add(mouse);

        // Convert details to lowercase before writing to file
        String mouseDetails = String.format("\n%d, mouse, %s, %s, %s, %s, %d, %.2f, %.2f, %d", 
            barcode, 
            type.toString().toLowerCase(), 
            brand.toLowerCase(), 
            colour.toLowerCase(), 
            connectivity.toString().toLowerCase(), 
            quantity, 
            originalCost, 
            retailPrice, 
            numOfButtons
        );

        // Write to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Stock.txt", true))) {
            writer.write(mouseDetails);
        }
    }
 


    public String toString() {
        return "User details: " +
                "User ID: " + getUserId() + ",\n" +
                "Username: " + getUsername() + ",\n" +
                "Name: " + getName() + ",\n" +
                "Address: " + getAddress() + ",\n" +
                "Role: " + getRole() + ",\n";
    }
}

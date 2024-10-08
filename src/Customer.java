import java.io.IOException;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class Customer extends User {

    private ArrayList<Product> productsInBasket;

    public Customer(int userId, String username, String name, Address address) {
        super(userId, username, name, address, Role.CUSTOMER);
        this.productsInBasket = new ArrayList<>();
    }

    //Customer Specific functions

    //View all products sorted by retail price ASC (BUT NOT the original cost)
    @Override
    public ArrayList<String> viewAllProducts(ArrayList<Product> products) {
        Collections.sort(products, Comparator.comparing(Product::getRetailPrice));

        ArrayList<String> sortedProductsToPrint = new ArrayList<>();
        for (Product product: products) {
            sortedProductsToPrint.add(getProductForCustomer(product));
        }

        return sortedProductsToPrint;
    }

	 public static String getProductForCustomer(Product product) {
	        if (product instanceof Mouse) {
	            return ((Mouse) product).toStringForCustomer();
	        } else if (product instanceof Keyboard) {
	            return ((Keyboard) product).toStringForCustomer();
	        } else {
	            // Handle other types of products if needed
	            return product.toString(); // Fallback to default toString
	        }
	    }

    //Add to shopping basket if product is not out of stock
    public void addToBasket(Product product) throws Exception {
    	if (!isAvailable(product, 1)) {
    		throw new Exception("This product is Out of Stock.");
    	} else {
    		productsInBasket.add(product);
    	}
   
    }



    //Cancel shopping basket
    public void clearBasket() {
        productsInBasket.clear();
    }


    //Get total cost
    public double getTotalCost() {
        double totalCost = 0;
        for (Product product : productsInBasket) {
            double productPrice = product.getRetailPrice();
            totalCost += productPrice;
        }
        return totalCost;
    }


    //View their shopping basket
    public ArrayList<Product> viewBasket() {
        //print products
        /*String basketString = "Basket:\n";

        for (Product product : productsInBasket) {
            basketString += product.toStringForCustomer() + "\n\n";
        }
        //print total cost
        basketString += "Total Cost: " + getTotalCost();

        return basketString; */
    	return new ArrayList<>(productsInBasket);
    	
    }



    //Complete payment by paypal/credit card. Checks if basket is empty first.
    
    public Receipt completePayment(PaymentMethod paymentMethod, Address address) throws Exception {
        double totalCost = getTotalCost();
        Receipt receipt;

        if (totalCost == 0) {
        	throw new Exception("Your basket is empty. Please add items to your basket before payment.");
        }

        // Check product stock availability
        for (Product product : productsInBasket) {
            int quantityInBasket = Collections.frequency(productsInBasket, product);
            if (!isAvailable(product, quantityInBasket)) {
                throw new Exception("Insufficient stock for product " + product.getBarcode() + ". Available quantity: " + product.getQuantityInStock());
            }
        }


        // Process payment
        receipt = paymentMethod.processPayment(totalCost, address);

        if (receipt != null) {
            updateStock();
            clearBasket();
        }
        return receipt;
    }


    //COMPLETE PAYMENT - CONSOLE VERSION 
    /*public Receipt completePayment(PaymentMethod paymentMethod, Address address) {
        double totalCost = getTotalCost();
        Receipt receipt;
        
        if (totalCost == 0) {
            System.out.println("Your basket is empty. Please add items to your basket before payment.");
            return null;
        }

        // Check product stock availability
        for (Product product : productsInBasket) {
            if (!isAvailable(product, 1)) {
                System.out.println("Insufficient stock for product " + product.getBarcode());
                return null;
            }
            
        }
        
        //Check which payment method is used + generate receipt
        if (paymentMethod instanceof Paypal) {
            //get paypal details
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your PayPal email:");
            String email = scanner.nextLine();
            
            if (!validEmail(email)) {
            	System.out.println("Invalid email address. Try again.");
            	return null;
            }

            Paypal paypal = new Paypal(email); //new paypal object

            receipt = paypal.processPayment(totalCost, address);


        } else if (paymentMethod instanceof CreditCard) {
            //get credit card details
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your Credit Card number:");
            int cardNum = scanner.nextInt();
            System.out.println("Enter your Credit Card security number:");
            int securityNum = scanner.nextInt();
            
            if (!validCreditCardDetails(cardNum, securityNum)) {
            	System.out.println("Invalid Credit Card Details. Try again.");
            }

            CreditCard creditCard = new CreditCard(cardNum, securityNum);//new credit object

            receipt = creditCard.processPayment(totalCost, address);

        } else {
            System.out.println("Invalid Payment Method. Try Again.");
            return null;
        }
        
        updateStock();
        clearBasket();
        return receipt;

    }*/

    //checks if valid email
    public boolean validEmail(String email) {
    	return email.contains("@") && email.contains(".");
    }
    
    //checks if valid credit card
    public boolean validCreditCardDetails(int cardNum, int securityNum) {
    	return String.valueOf(cardNum).length() == 6 && String.valueOf(securityNum).length() == 3;
    }
    
    //Writes to the file and updates the stock when a customer buys a product
    public void updateStock() {
    	File originalFile = new File("Stock.txt");
		File tempFile = new File("Temp-Stock.txt");
    	try {
    		
    		BufferedReader reader = new BufferedReader(new FileReader(originalFile));
    		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
    		
    		String currentLine;
    		while ((currentLine = reader.readLine()) != null) {
    			String [] productInfo = currentLine.split(", ");
    			int productBarcode = Integer.parseInt(productInfo[0]);
    			
    			for (Product product : productsInBasket) {
    				if (product.getBarcode() == productBarcode) {
    					int quantityInStock = Integer.parseInt(productInfo[6]);
    					if (quantityInStock > 0) {
    						productInfo[6] = String.valueOf(quantityInStock - 1); //update file entry
    						product.setQuantityInStock(quantityInStock - 1); //update product object
    					}
    				}
    			}
    			writer.write(String.join(", ", productInfo) + System.lineSeparator());
    		}
    		
    		writer.close();
    		reader.close();
    		
  
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    
    	originalFile.delete();
    	tempFile.renameTo(originalFile);
    }
    
    //checks if a product is available or not.
    private boolean isAvailable(Product product, int quantityBought) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Stock.txt"));
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                String[] productInfo = currentLine.split(", ");
                int productBarcode = Integer.parseInt(productInfo[0]);
                if (product.getBarcode() == productBarcode) {
                    int quantityInStock = Integer.parseInt(productInfo[6]);
                    return quantityInStock >= quantityBought;
                }
                
            }
            reader.close();
            

        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    
    
    //search by barcode
    public Product searchByBarcode(ArrayList<Product> products, int barcode) {
        for (Product product : products) {
            if (product.getBarcode() == barcode) {
                return product;
            }
        }
        return null;
    }

    //filter mice based on num of mouse buttons
    public ArrayList<Product> filterMiceByButtons(ArrayList<Product> products, int buttons) {
        ArrayList<Product> filteredProducts = new ArrayList<>();
        for (Product product : products) {
            if (product instanceof Mouse && ((Mouse) product).getNumOfButtons() == buttons) {
                filteredProducts.add(product);
            }
        }
        return filteredProducts;
    }

    // search and filter methods using Scanner
    /*public void searchAndFilterProducts(ArrayList<Product> products) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose an option:\n1. Search by barcode\n2. Filter mice by number of buttons");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Enter barcode to search:");
            int barcode = scanner.nextInt();
            Product product = searchByBarcode(products, barcode);
            if (product != null) {
                System.out.println("Product found:\n" + product.toStringForCustomer());
            } else {
                System.out.println("No product found with the given barcode.");
            }
        } else if (choice == 2) {
            System.out.println("Enter the number of buttons to filter mice:");
            int buttons = scanner.nextInt();
            ArrayList<Product> filteredProducts = filterMiceByButtons(products, buttons);
            if (filteredProducts.isEmpty()) {
                System.out.println("No mice found with the given number of buttons.");
            } else {
                System.out.println("Filtered mice:");
                for (Product product : filteredProducts) {
                    System.out.println(product.toStringForCustomer());
                }
            }
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }*/




    public String toString() {
        return "User details: " +
                "User ID: " + getUserId() + ",\n" +
                "Username: " + getUsername() + ",\n" +
                "Name: " + getName() + ",\n" +
                "Address: " + getAddress() + ",\n" +
                "Role: " + getRole() + ",\n";
    }




}

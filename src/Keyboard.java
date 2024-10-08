public class Keyboard extends Product{

    private final KeyboardLayout layout;
    private final KeyboardType type;

    public Keyboard(int barcode, String brand, String color,
                    ConnectivityType connectivity, int quantityInStock,
                    double originalCost, double retailPrice,
                    ProductCategory category, KeyboardLayout layout, KeyboardType type) {

        super(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, category);
        this.layout = layout;
        this.type = type;
    }

    public KeyboardLayout getLayout() {
        return layout;
    }

    public KeyboardType getType() {
        return type;
    }

 
    //for admin, the keyboard properties as a string
    @Override
    public String toString() {
        return getBarcode() + ", "
                + getCategory() + ", "
                + getType() + ", "
                + getBrand() + ", "
                + getColor() + ", "
                + getConnectivity() + ", "
                + getQuantityInStock() + ", "
                + getOriginalCost() + ", "
                + getRetailPrice() + ", "
                + getLayout();
    }

    
    //for customer, the keyboard attributes but without original price
    public String toStringForCustomer() {
        return getBarcode() + ", " + getBrand() + ", " + getColor() + ", " + getConnectivity() + ", "
                + getQuantityInStock() + ", "
                + getRetailPrice() + ", "
                + getCategory() + ", "
                + getType() + ", "
                + getLayout() + "Layout";
    }


}

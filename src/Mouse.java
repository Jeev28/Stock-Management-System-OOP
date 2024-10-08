public class Mouse extends Product{

    private final MouseType type;
    private final int numOfButtons;

    public Mouse(int barcode, String brand, String color,
                 ConnectivityType connectivity, int quantityInStock,
                 double originalCost, double retailPrice,
                 ProductCategory category, MouseType type, int numOfButtons) {
        super(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, category);
        this.type = type;
        this.numOfButtons = numOfButtons;
    }

    public MouseType getType() {
        return type;
    }

    public int getNumOfButtons() {
        return numOfButtons;
    }


    //the admin string of the mouse attributes
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
                + getNumOfButtons() + "Btns";
    }

    
    //the customer string of the mouse attributes without original price
    public String toStringForCustomer() {
        return  getBarcode() + ", "
                + getBrand() + ", "
                + getColor() + ", "
                + getConnectivity() + ", "
                + getQuantityInStock() + ", "
                + getRetailPrice() + ", "
                + getCategory() + ", "
                + getType() + ", "
                + getNumOfButtons() + "Btns";
    }


}

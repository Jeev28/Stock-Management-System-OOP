public abstract class Product {


    private final int barcode;
    private final String brand;
    private final String color;
    private final ConnectivityType connectivity;
    private int quantityInStock;
    private final double originalCost;
    private final double retailPrice;
    private final ProductCategory category;

    public Product(int barcode, String brand, String color,
                   ConnectivityType connectivity, int quantityInStock,
                   double originalCost, double retailPrice,
                   ProductCategory category) {

        this.barcode = barcode;
        this.brand = brand;
        this.color = color;
        this.connectivity = connectivity;
        this.quantityInStock = quantityInStock;
        this.originalCost = originalCost;
        this.retailPrice = retailPrice;
        this.category = category;
    }

    public int getBarcode() {
        return barcode;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }

    public ConnectivityType getConnectivity() {
        return connectivity;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public double getOriginalCost() {
        return originalCost;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public ProductCategory getCategory() {
        return category;
    }

    @Override
    public abstract String toString();

}


public class ProductString {
	 public static String getProductForCustomer(Product product) {
	        if (product instanceof Mouse) {
	            return ((Mouse) product).toStringForCustomer();
	        } else if (product instanceof Keyboard) {
	            return ((Keyboard) product).toStringForCustomer();
	        } else {

	            return product.toString(); // normal toString
	        }
	    }
}

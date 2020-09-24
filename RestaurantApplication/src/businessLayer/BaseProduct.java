package businessLayer;

public class BaseProduct extends  MenuItem{
    public BaseProduct(String productName, double price) {
        super(productName, price);
    }

    @Override
    double computePrice() {
        return getPrice();
    }
}

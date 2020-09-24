package businessLayer;

import java.util.ArrayList;

public class CompositeProduct extends MenuItem {
    ArrayList<MenuItem> composition;

    public CompositeProduct(String productName) {
        super(productName);
        composition = new ArrayList<MenuItem>();
    }

    public void addProduct(MenuItem prod) {
        composition.add(prod);
    }

    public ArrayList<MenuItem> getComposition() {
        return composition;
    }

    public boolean findItem(String name) {
        for (MenuItem i : composition) {
            if (i.getProductName().equals(name)) {
                return true;
            }
        }
        return false;
    }
    @Override
    double computePrice() {
        double totalPrice = 0;
        for(MenuItem i: composition)
            totalPrice += i.computePrice();
        return totalPrice;
    }
}

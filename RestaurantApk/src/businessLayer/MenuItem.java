package businessLayer;

import java.io.Serializable;

public abstract class MenuItem implements Serializable {
    private String productName;
    private double price;

    public MenuItem(String productName) {
        this.productName = productName;
    }
    public MenuItem(String productName, double price) {
        this.productName = productName;
        this.price = price;
    }

    abstract double computePrice();

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString(){
        return productName;
    } //folosit pentru afisarea in choice box-uri[
}

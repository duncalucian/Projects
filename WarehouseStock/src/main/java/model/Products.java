package model;

/**
 * Class that represents the data format stored into the Products table. Each field
 * corresponds to a column.
 */
public class Products {
    /**
     * Id-ul produsului
     */
    private int productId;
    /**
     * Numele produsului
     */
    @CheckToDelete
    private String product_name;
    /**
     * Cantitate disponibila.
     */
    private int quantity;
    /**
     * Prestul produsului
     */
    private double price;
    /**
     * Flag pentru delete.
     */
    private int deleted;

    public Products() {
    }

    public Products(String product_name, int quantity, double price, int deleted) {
        this.product_name = product_name;
        this.quantity = quantity;
        this.price = price;
        this.deleted = deleted;
    }

    public Products(String product_name) {
        this.product_name = product_name;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}

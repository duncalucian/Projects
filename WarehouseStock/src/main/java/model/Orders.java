package model;

    /**
     * Class that represents the data format stored into the Orders table. Each field
     * corresponds to a column.
     */
public class Orders {
    /**
     *  Id-ul comenzii
     */
    private int orderId;
    /**
     *  Numele clientului care a plasat comanda
     */
    @CheckToDelete
    private String cname;
    /**
     * Produsul comandat
     */
    private String pname;
    /**
     * Cantitatea comandata
     */
    private int quantity;
    /**
     * Flag pentru delete
     */
    private int deleted;


    /**
     * Constructor mostly used to obtain a instance of the class
     * with .newInstance() method.
     */
    public Orders() {
    }

    public Orders(String cname, String pname, int quantity, int deleted) {
        this.cname = cname;
        this.pname = pname;
        this.quantity = quantity;
        this.deleted = deleted;
    }

    public Orders(String cname) {
        this.cname = cname;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}

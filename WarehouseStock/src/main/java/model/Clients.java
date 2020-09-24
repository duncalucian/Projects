package model;

/**
 * Class that represents the data format stored into the Clients table. Each field
 * corresponds to a column.
 */
public class Clients {
    /**
     * Id-ul clientului
     */
    private int clientId;
    /**
     * numele clientului
     */
    @CheckToDelete
    private String client_name;
    /**
     * Adresa clientului
     */
    @CheckToDelete
    private String address;
    /**
     * Flag pentru operatia de delte.
     */
    private int deleted;

    /**
     * Constructor mostly used to obtain a instance of the class
     * with .newInstance() method.
     */
    public Clients() {
    }

    public Clients(String client_name, String address, int deleted) {
        this.client_name = client_name;
        this.address = address;
        this.deleted = deleted;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
}

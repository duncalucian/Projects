package businessLayer;

import java.time.LocalDate;
import java.util.Objects;

public class Order {
    private int orderId;
    private int tableNumber;
    private static int numOfOrders = 1;
    private LocalDate date;
    private boolean billed;

    public Order(int tableNumber, LocalDate date) {
        this.orderId = numOfOrders;
        this.tableNumber = tableNumber;
        this.date = date;
        billed = false;
        numOfOrders++;
    }

    public int getTableNumber() {
        return tableNumber;
    }


    public int getOrderId() {
        return orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isBilled() {
        return billed;
    }

    public void setBilled(boolean billed) {
        this.billed = billed;
    }

    @Override
    public int hashCode() {
        final int prime = 7;
        int result = 1;
        result = prime * result + orderId;
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId == order.orderId &&
                Objects.equals(date, order.date);
    }
}

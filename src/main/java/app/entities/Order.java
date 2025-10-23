package app.entities;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private User user;
    private List<Cupcake> cupcakes = new ArrayList<>();
    private double totalPrice;
    private boolean paid;

    public Order(int orderId, User user, List<Cupcake> cupcakes, double totalPrice, boolean paid) {
        this.orderId = orderId;
        this.user = user;
        this.cupcakes = cupcakes;
        this.totalPrice = totalPrice;
        this.paid = paid;
    }

    public void addCupcake(Cupcake cupcake) {
        cupcakes.add(cupcake);
        totalPrice += cupcake.getTotalPrice();
    }

    public int getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public List<Cupcake> getCupcakes() {
        return cupcakes;
    }
}
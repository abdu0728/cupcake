package app.entities;

public class Bund {
    private int bundId;
    private String name;
    private double price;

    public Bund(int bundId, String name, double price) {
        this.bundId = bundId;
        this.name = name;
        this.price = price;
    }
public int getBundId() {
        return bundId;
}
public String getName() {
        return name;
}
public double getPrice() {
        return price;
}
@Override
    public String toString() {
        return "Bund{" +
                "BundId=" + bundId +
                ", name=" + name +
                ", price=" + price +
                '}';
    }
}

package app.entities;

public class Cupcake {
    private Bund bund;
    private Top top;

    public Cupcake(Bund bund, Top top) {
        this.bund = bund;
        this.top = top;
    }

    public Bund getBund() {
        return bund;
    }

    public Top getTop() {
        return top;
    }

    public double getTotalPrice() {
        return bund.getPrice() + top.getPrice();
    }

    @Override
    public String toString() {
        return bund.getName() + " + " + top.getName() + " = " + getTotalPrice();
    }
}
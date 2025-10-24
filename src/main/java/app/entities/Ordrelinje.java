package app.entities;

public class Ordrelinje {
    private int ordreLinjeId;
    private int ordreId;
    private int bundId;
    private int topId;
    private int antal;
    private double linjePris;

    public Ordrelinje(int ordreLinjeId, int ordreId, int bundId, int topId, int antal, double linjePris) {
        this.ordreLinjeId = ordreLinjeId;
        this.ordreId = ordreId;
        this.bundId = bundId;
        this.topId = topId;
        this.antal = antal;
        this.linjePris = linjePris;
    }
    public int getOrdreLinjeId() {
        return ordreLinjeId;
    }
    public int getOrdreId() {
        return ordreId;
    }
    public int getBundId() {
        return bundId;
    }
    public int getTopId() {
        return topId;
    }
    public int getAntal() {
        return antal;
    }
    public double getLinjePris() {
        return linjePris;
    }
    @Override
    public String toString() {
        return "Ordrelinje{" +
                "ordreLinjeId=" + ordreLinjeId +
                ", ordreId=" + ordreId +
                ", bundId=" + bundId +
                ", topId=" + topId +
                ", antal=" + antal +
                ", linjePris=" + linjePris +
                '}';
    }
}

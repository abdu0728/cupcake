package app.entities;

public class Topping {
    private int topId;
    private String navn;
    private double pris;
    private boolean aktiv;

    public Topping(int id, String navn, double pris, boolean aktiv) {
        this.topId = id;
        this.navn = navn;
        this.pris = pris;
        this.aktiv = aktiv;
    }
    public int getTopId() {

        return topId;
    }
    public String getNavn() {

        return navn;
    }
    public double getPris() {

        return pris;
    }
    public boolean isAktiv() {
        return aktiv;
    }

    public void setPris(double pris) {
        this.pris = pris;
    }
    public void setNavn(String navn) {
        this.navn = navn;
    }
    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }
    public void setTopId(int topId) {
        this.topId = topId;
    }

    @Override
    public String toString() {
        return "Topping{" +
                "topId=" + topId +
                ", navn=" + navn +
                ", pris=" + pris +
                ", aktiv=" + aktiv +
                '}';
    }
}

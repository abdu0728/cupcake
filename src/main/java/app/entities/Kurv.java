package app.entities;

public class Kurv {
    private int kurvId;
    private int brugerId;
    private int bundId;
    private int topId;
    private int antal;
    private String tilfojet;

    public Kurv (int kurvId, int brugerId, int bundId, int topId, int antal, String tilfojet){
        this.kurvId = kurvId;
        this.brugerId = brugerId;
        this.bundId = bundId;
        this.topId = topId;
        this.antal = antal;
        this.tilfojet = tilfojet;
    }

    public Kurv (int brugerId, int bundId, int topId, int antal){
        this.brugerId = brugerId;
        this.bundId = bundId;
        this.topId = topId;
        this.antal = antal;
    }
    public int getKurvId() {
        return kurvId;
    }
    public int getBrugerId() {
        return brugerId;
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
    public String getTilfojet() {
        return tilfojet;
    }

    @Override
    public String toString() {
        return "Kurv{" +
                "kurvId=" + kurvId +
                ", brugerId=" + brugerId +
                ", bundId=" + bundId +
                ", topId=" + topId +
                ", antal=" + antal +
                ", tilfojet=" + tilfojet +
                '}';
    }
}

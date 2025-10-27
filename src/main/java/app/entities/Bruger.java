package app.entities;

public class Bruger {
    private int brugerId;
    private String email;
    private String kodeord;
    private String rolle;
    private double saldo;
    private boolean oprettet;


    public Bruger(int brugerId, String email, String kodeord, String rolle, double saldo, boolean oprettet) {
        this.brugerId = brugerId;
        this.email = email;
        this.kodeord = kodeord;
        this.rolle = rolle;
        this.saldo = saldo;
        this.oprettet = oprettet;
    }
    public int getBrugerId() {

        return brugerId;
    }
    public String getEmail() {

        return email;
    }
    public String getKodeord() {

        return kodeord;
    }
    public String getRolle() {

        return rolle;
    }
    public double getSaldo() {

        return saldo;
    }
    public boolean isOprettet() {

        return oprettet;
    }
    public boolean erAdmin() {

        return rolle != null && rolle.equalsIgnoreCase("administrator");
    }

    @Override
    public String toString() {
        return "Bruger{" +
                "brugerId=" + brugerId +
                ", email=" + email +
                ", rolle=" + rolle +
                ", saldo=" + saldo +
                ", oprettet=" + oprettet +
                '}';
    }
}

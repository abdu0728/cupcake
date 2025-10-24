package app.entities;

import java.time.LocalDateTime;

public class Ordre {
    private int ordreId;
    private int brugerId;
    private double totalPris;
    private String status;
    private LocalDateTime ordreDato;
    private LocalDateTime afhentningsDato;

    public Ordre(int ordreId, int brugerId, String status, LocalDateTime ordreDato, LocalDateTime afhentningsDato) {
        this.ordreId = ordreId;
        this.brugerId = brugerId;
        this.totalPris = totalPris;
        this.status = status;
        this.ordreDato = ordreDato;
        this.afhentningsDato = afhentningsDato;
    }

    public int getOrdreId() { return ordreId; }
    public int getBrugerId() { return brugerId; }
    public double getTotalPris() { return totalPris; }
    public String getStatus () { return status; }
    public LocalDateTime getOrdreDato() { return ordreDato; }
    public LocalDateTime getAfhentningsDato() { return afhentningsDato; }

    @Override
    public String toString() {
        return "Ordre{" +
                "ordreId=" + ordreId +
                ", brugerId=" + brugerId +
                ", totalPris=" + totalPris +
                ", status=" + status + '\'' +
                ", ordreDato=" + ordreDato +
                ", afhentningsDato=" + afhentningsDato +
                '}';
        }
    }
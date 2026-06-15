package model;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private int userId;
    private int voitureId;
    private int nbJr;
    private String status;
    private Timestamp dateReservation;
    private LocalDate dateDebut;
    private double montantTotal;
    private String username;
    private String voiture;

    public Reservation() {}

    public Reservation(int id, int userId, int voitureId, int nbJr, String status, Timestamp dateReservation, LocalDate dateDebut, double montantTotal, String username, String voiture) {
        this.id = id;
        this.userId = userId;
        this.voitureId = voitureId;
        this.nbJr = nbJr;
        this.status = status;
        this.dateReservation = dateReservation;
        this.dateDebut = dateDebut;
        this.montantTotal = montantTotal;
        this.username = username;
        this.voiture = voiture;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVoitureId() {
        return voitureId;
    }

    public void setVoitureId(int voitureId) {
        this.voitureId = voitureId;
    }

    public int getNbJr() {
        return nbJr;
    }

    public void setNbJr(int nbJr) {
        this.nbJr = nbJr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Timestamp dateReservation) {
        this.dateReservation = dateReservation;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getVoiture() {
        return voiture;
    }

    public void setVoiture(String voiture) {
        this.voiture = voiture;
    }


}

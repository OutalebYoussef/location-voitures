package model;

public class Voiture {
    private int id;
    private String marque;
    private String modele;
    private String matricule;
    private double prixJour;
    private String statut;

    public Voiture() {}

    public Voiture(String marque, String modele,
                   String matricule, double prixJour,
                   String statut) {
        this.marque = marque;
        this.modele = modele;
        this.matricule = matricule;
        this.prixJour = prixJour;
        this.statut = statut;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public double getPrixJour() {
        return prixJour;
    }

    public void setPrixJour(double prixJour) {
        this.prixJour = prixJour;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}

package com.example.gestculture.Metier;

public class Visiter {
    private String matricule;
    private int idExploitation;
    private String annee;

    public Visiter(String matricule, int idExploitation, String annee) {
        this.matricule = matricule;
        this.idExploitation = idExploitation;
        this.annee = annee;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public int getIdExploitation() {
        return idExploitation;
    }

    public void setIdExploitation(int idExploitation) {
        this.idExploitation = idExploitation;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }
}

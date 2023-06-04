package com.example.gestculture.Metier;

public class Parcelle {
    private String annee;
    private int idExploitation;
    private int idParcelle;
    private String surface;
    private String decoupage;
    private String rendementReel;
    private String rendementPrevu;
    private int code;

    public Parcelle(String annee, int idExploitation, int idParcelle, String surface, String decoupage, String rendementReel, String rendementPrevu, int code) {
        this.annee = annee;
        this.idExploitation = idExploitation;
        this.idParcelle = idParcelle;
        this.surface = surface;
        this.decoupage = decoupage;
        this.rendementReel = rendementReel;
        this.rendementPrevu = rendementPrevu;
        this.code = code;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public int getIdExploitation() {
        return idExploitation;
    }

    public void setIdExploitation(int idExploitation) {
        this.idExploitation = idExploitation;
    }

    public int getIdParcelle() {
        return idParcelle;
    }

    public void setIdParcelle(int idParcelle) {
        this.idParcelle = idParcelle;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getDecoupage() {
        return decoupage;
    }

    public void setDecoupage(String decoupage) {
        this.decoupage = decoupage;
    }

    public String getRendementReel() {
        return rendementReel;
    }

    public void setRendementReel(String rendementReel) {
        this.rendementReel = rendementReel;
    }

    public String getRendementPrevu() {
        return rendementPrevu;
    }

    public void setRendementPrevu(String rendementPrevu) {
        this.rendementPrevu = rendementPrevu;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

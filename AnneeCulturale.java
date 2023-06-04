package com.example.gestculture.Metier;

import java.util.Calendar;

public class AnneeCulturale {

    private String annee;

    public AnneeCulturale(String annee) {
        this.annee = annee;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public void calculAnneeCulturelle() {
        Calendar cal = Calendar.getInstance();
        int annees = cal.get(Calendar.YEAR);
        int mois = cal.get(Calendar.MONTH);
        if (mois >= Calendar.JULY) {
            annees += 1;
        }
        int debut = annees - 1;
        int fin = annees;
         annee = debut + "-" + fin;
    }
}

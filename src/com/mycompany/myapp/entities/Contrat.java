/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author msi
 */
public class Contrat {
    
    private int id;
    private String montant;
    private String dateexpiration;
    private String imagecontrat;
    private String datesignature;

    public Contrat() {
    }

    public Contrat(int id, String montant, String dateexpiration, String imagecontrat, String datesignature) {
        this.id = id;
        this.montant = montant;
        this.dateexpiration = dateexpiration;
        this.imagecontrat = imagecontrat;
        this.datesignature= datesignature;
    }

    public Contrat(String montant, String dateexpiration, String imagecontrat, String datesignature) {
        this.montant = montant;
        this.dateexpiration = dateexpiration;
        this.imagecontrat = imagecontrat;
        this.datesignature = datesignature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMontant() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant = montant;
    }

    public String getDateexpiration() {
        return dateexpiration;
    }

    public void setDateexpiration(String dateexpiration) {
        this.dateexpiration = dateexpiration;
    }

    public String getImagecontrat() {
        return imagecontrat;
    }

    public void setImagecontrat(String imagecontrat) {
        this.imagecontrat = imagecontrat;
    }

    public String getDatesignature() {
        return datesignature;
    }

    public void setDatesignature(String datesignature) {
        this.datesignature = datesignature;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.entities;

import java.sql.Date;

/**
 *
 * @author pc
 */
public class contrat {
    Integer id , montant,rating ; 

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
    Date datesignature ,dateexpiration ; 
    String imagecontrat , nomcontrat ;  

    public contrat() {
    }


    public contrat(Integer id, Integer montant, Date datesignature, Date dateexpiration, String imagecontrat, String nomcontrat) {
        this.id = id;
        this.montant = montant;
        this.datesignature = datesignature;
        this.dateexpiration = dateexpiration;
        this.imagecontrat = imagecontrat;
        this.nomcontrat = nomcontrat;
    }

    public contrat(Integer montant, Date datesignature, Date dateexpiration, String imagecontrat, String nomcontrat) {
        this.montant = montant;
        this.datesignature = datesignature;
        this.dateexpiration = dateexpiration;
        this.imagecontrat = imagecontrat;
        this.nomcontrat = nomcontrat;
    }

    public contrat(Integer id, String nomcontrat) {
        this.id = id;
        this.nomcontrat = nomcontrat;
    }
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDatesignature() {
        return datesignature;
    }

    public void setDatesignature(Date datesignature) {
        this.datesignature = datesignature;
    }

    public Date getDateexpiration() {
        return dateexpiration;
    }

    public void setDateexpiration(Date dateexpiration) {
        this.dateexpiration = dateexpiration;
    }

    public Integer getMontant() {
        return montant;
    }

    public void setMontant(Integer montant) {
        this.montant = montant;
    }

    public String getImagecontrat() {
        return imagecontrat;
    }

    public void setImagecontrat(String imagecontrat) {
        this.imagecontrat = imagecontrat;
    }

    public String getNomcontrat() {
        return nomcontrat;
    }

    public void setNomcontrat(String nomcontrat) {
        this.nomcontrat = nomcontrat;
    }

    @Override
    public String toString() {
        return "contrat{" + "id=" + id + ", montant=" + montant + ", datesignature=" + datesignature + ", dateexpiration=" + dateexpiration + ", imagecontrat=" + imagecontrat + ", nomcontrat=" + nomcontrat + '}';
    }

  
    
    

  

    
}

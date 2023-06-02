/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edu.project.entities;

/**
 *
 * @author pc
 */
public class TypeContrat {
    Integer id, contrat_id;   
    String nom  ;

    public TypeContrat() {
    }

    public TypeContrat(Integer id, Integer contrat_id, String nom) {
        this.id = id;
        this.contrat_id = contrat_id;
        this.nom = nom;
    }

    public TypeContrat(Integer contrat_id, String nom) {
        this.contrat_id = contrat_id;
        this.nom = nom;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getContrat_id() {
        return contrat_id;
    }

    public void setContrat_id(Integer contrat_id) {
        this.contrat_id = contrat_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "TypeContrat{" + "id=" + id + ", contrat_id=" + contrat_id + ", nom=" + nom + '}';
    }
    

    
    
}

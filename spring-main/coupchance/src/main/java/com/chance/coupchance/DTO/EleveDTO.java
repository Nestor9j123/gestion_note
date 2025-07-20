package com.chance.coupchance.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor

public class EleveDTO {
    private String matricule;
    private String nom;
    private String prenom;
    private String sexe;  // String pour la désérialisation facile
    private LocalDate dateNaissance;

    private String classe;  // Renommé pour correspondre au JSON envoyé par le frontend
    private int id;

    // Getters et Setters
    public String getMatricule() {
        return matricule;
    }
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getSexe() {
        return sexe;
    }
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public String getClasse() {
        return classe;  // Getter pour classe, correspond au champ du JSON
    }
    public void setClasse(String classe) {
        this.classe = classe;  // Setter pour classe
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Constructeur sans arguments (pour le cas des frameworks comme Spring qui utilisent la réflexion)
    public EleveDTO(String matricule, String nom, String prenom, String sexe, LocalDate dateNaissance, String classe, int id) {
        this.matricule = matricule;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.dateNaissance = dateNaissance;
        this.classe = classe;
        this.id = id;
    }
}

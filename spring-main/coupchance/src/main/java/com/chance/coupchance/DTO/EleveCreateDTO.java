package com.chance.coupchance.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
public class EleveCreateDTO {
    private String matricule;
    private String nom;
    private String prenom;
    private String sexe; // String pour la désérialisation facile
    private LocalDate dateNaissance;
    private String classeNom; // Juste le nom de la classe
    
    // Getters et Setters
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    public String getClasseNom() { return classeNom; }
    public void setClasseNom(String classeNom) { this.classeNom = classeNom; }
    public EleveCreateDTO(int id, String matricule2, String nom2, String prenom2, String name, LocalDate dateNaissance2,
            Object object) {
        //TODO Auto-generated constructor stub
    }
}
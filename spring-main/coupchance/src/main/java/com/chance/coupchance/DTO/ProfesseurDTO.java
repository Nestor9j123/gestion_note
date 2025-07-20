package com.chance.coupchance.DTO;

import java.util.List;

import javax.print.DocFlavor.STRING;

import com.chance.coupchance.Entites.Classe;

public class ProfesseurDTO {
    public Long id;
    public String nom;
    public String prenom;
    public List<MatieresDTO> matieres;
    public List<String> classes;


    public ProfesseurDTO() {
        // Constructeur par défaut
    }
    //getter et setter
  
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public List<MatieresDTO> getMatieres() { return matieres; }
    public void setMatieres(List<MatieresDTO> matieresDTO) { this.matieres = matieresDTO; }
    
    public List<String> getClasses() { return classes; }
    public void setClasses(List<String> classes) { this.classes = classes; }
}



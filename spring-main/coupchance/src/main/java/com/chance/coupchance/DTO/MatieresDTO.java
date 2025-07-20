package com.chance.coupchance.DTO;

public class MatieresDTO {
    public String nom;
    public double coefficient;
    public boolean college; // Nouveau champ pour indiquer si c'est pour Collège
    public boolean lycee;   // Nouveau champ pour indiquer si c'est pour Lycée
    //constructeur par defaut
    public MatieresDTO() {
    }
    //constructeur avec parametres
    public MatieresDTO(String nom, double coefficient, boolean college, boolean lycee) {
        this.nom = nom;
        this.coefficient = coefficient;
        this.college = college;
        this.lycee = lycee;
    }
}


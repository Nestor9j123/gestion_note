package com.chance.coupchance.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter

@Setter
public class MatiereCoefficientDTO {
    public String nom;
    public double coefficient;

    public MatiereCoefficientDTO() {
        // Constructeur par défaut
    }
    
    public MatiereCoefficientDTO(String nom, double coefficient) {
        this.nom = nom;
        this.coefficient = coefficient;
    }
}
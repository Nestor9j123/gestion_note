package com.chance.coupchance.DTO;

import com.chance.coupchance.Entites.Matiere;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatiereDTO {
    private Long id;
    public String nom;
    public double coefficient;
    public boolean college;
    public boolean lycee;
    private String nomProfesseur;
    private String prenomProfesseur;

    public MatiereDTO(Matiere matiere) {
        this.id = matiere.getId();
        this.nom = matiere.getNom();
        this.coefficient = matiere.getCoefficient();
        this.college = matiere.isCollege();
        this.lycee = matiere.isLycee();

        // Nom et prénom du professeur si existant
        if (matiere.getProfesseur() != null) {
            this.nomProfesseur = matiere.getProfesseur().getNom();
            this.prenomProfesseur = matiere.getProfesseur().getPrenom();
        }
    }
    // Constructeur par défaut
    public MatiereDTO() {
        // Constructeur par défaut
    }
    // Constructeur pour nom
    public MatiereDTO(String nom, double d, boolean b, boolean c) {
        this.nom = nom;
    }
}

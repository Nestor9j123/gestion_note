package com.chance.coupchance.DTO;

import com.chance.coupchance.Entites.Classe;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClasseDTO {
    private Long id;
    public String nom;
    public int effectif;

    // Constructeur pour la désérialisation
    @JsonCreator
    public ClasseDTO(
            @JsonProperty("id") Long id,
            @JsonProperty("nom") String nom,
            @JsonProperty("effectif") int effectif) {
        this.id = id;
        this.nom = nom;
        this.effectif = effectif;
    }

    // Constructeur à partir de l'entité Classe
    public ClasseDTO(Classe classe) {
        this.id = classe.getId();
        this.nom = classe.getNom();
        this.effectif = classe.getEleves() != null ? classe.getEleves().size() : 0;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public String getNom() { return nom; }
    public int getEffectif() { return effectif; }

    public void setId(Long id) { this.id = id; }
    public void setNom(String nom) { this.nom = nom; }
    public void setEffectif(int effectif) { this.effectif = effectif; }
}
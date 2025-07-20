package com.chance.coupchance.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "eleve")
public class Eleves {
    
    public enum Sexe {
        Male,
        Female
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String nom;
    private String prenom;
    
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    
    private LocalDate dateNaissance;
    private String matricule;
    
    @ManyToOne
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classe;
    
    @JsonIgnore
    @OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL)
    private List<Note> notes;
    
    @JsonIgnore
    @OneToMany(mappedBy = "eleve", cascade = CascadeType.ALL)
    private List<Bulletin> bulletins;

    @JsonProperty("id")
    public int getId() {
        return id;
    }
}
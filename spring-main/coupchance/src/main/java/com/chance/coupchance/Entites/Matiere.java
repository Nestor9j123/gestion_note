package com.chance.coupchance.Entites;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import com.chance.coupchance.DTO.MatiereDTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "matiere")
public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nom;
    private double coefficient;

    private boolean college; // Nouveau champ pour indiquer si c'est pour Collège
    private boolean lycee;   // Nouveau champ pour indiquer si c'est pour Lycée
    
    @ManyToOne
    @JoinColumn(name = "professeur_id")
    private Professeur professeur;
    
    @OneToMany(mappedBy = "matiere", cascade = CascadeType.ALL)
    private List<Note> notes;
     //setter pour nom

     // methode to string
   
}
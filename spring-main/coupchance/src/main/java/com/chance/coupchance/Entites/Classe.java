package com.chance.coupchance.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@Table(name = "classe")
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    public String nom;

    public Classe() {}
    
    public int effectif;

    @JsonIgnore
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL)
    private List<Eleves> eleves;
    
    @ManyToOne
    @JoinColumn(name = "professeur_id")
    private Professeur professeur;
    //getter and setter for nom
}
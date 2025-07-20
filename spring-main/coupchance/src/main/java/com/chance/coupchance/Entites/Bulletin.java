package com.chance.coupchance.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter 
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bulletin")
public class Bulletin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int trimestre;
    private String anneeScolaire;
    private String classe;
    private LocalDate dateBulletin;
    private double moyenne;
    private int rang;
    private String mention;
    private int retards;
    private int absences;
    private int punitions;
    private int exclusions;
    private boolean honneur;
    private boolean felicitations;
    private boolean encouragements;
    private boolean avertissement;
    private boolean blame;
    private boolean travail;
    private boolean discipline;
    private boolean travailBlame;
    private boolean disciplineBlame;
    private String observations;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private Eleves eleve;

    @OneToMany(mappedBy = "bulletin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Note> notes;
}
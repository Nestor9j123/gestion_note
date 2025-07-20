package com.chance.coupchance.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "note")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int trimestre;
    private double valeur;

    @Column(name = "type")
    private String type; // "note1", "note2", "compo"

    private Integer absences;
    private Integer punitions;
    private Integer exclusions;
    private String observation;

    private Double coefficient;
    private Double note1;
    private Double note2;
    private Double composition;

    private Double classAverage; // Moyenne de la classe pour la matière
    private String rank; // Rang de l'élève pour la matière
    private String teacher; // Nom du professeur de la matière

    private Double termAverage;

    public Double getTermAverage() { return termAverage; }
    public void setTermAverage(Double termAverage) { this.termAverage = termAverage; }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "eleve_id")
    private Eleves eleve;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "matiere_id")
    private Matiere matiere;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "bulletin_id")
    private Bulletin bulletin;
}
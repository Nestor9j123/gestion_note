package com.chance.coupchance.Entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SavedState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private User user; // Référence à l'utilisateur
    
    private String currentClass;
    private int currentEleveIndex;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Eleves currentEleve;
    
    private LocalDateTime lastModified = LocalDateTime.now();
    
    // Flag pour les sauvegardes partagées
    private boolean isShared = false;
}
package com.chance.coupchance.Repos;

import com.chance.coupchance.Entites.Classe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, Long> {
    List<Classe> findByEffectif(int effectif);
    List<Classe> findByNomContaining(String nom);
    List<Classe> findByNom(String nom);

    @Query("SELECT DISTINCT c FROM Classe c LEFT JOIN FETCH c.eleves e ORDER BY e.nom")
    List<Classe> findAllWithEleves();
    
}
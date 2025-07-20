package com.chance.coupchance.Repos;

import com.chance.coupchance.Entites.Matiere;
import com.chance.coupchance.Entites.Professeur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    List<Matiere> findByNomContaining(String nom);
    List<Matiere> findByCoefficientGreaterThan(double coefficient);
    List<Matiere> findByProfesseurId(Long professeurId);
    @Query("SELECT m.professeur FROM Matiere m WHERE m.id = :matiereId")
    Optional<Professeur> findProfesseurByMatiereId(@Param("matiereId") Long matiereId);

    @Query("SELECT m FROM Matiere m WHERE " +
       "(:niveau = 'college' AND m.college = true) OR " +
       "(:niveau = 'lycee' AND m.lycee = true)")
List<Matiere> findByNiveau(@Param("niveau") String niveau);

}
package com.chance.coupchance.Repos;

import com.chance.coupchance.Entites.Matiere;
import com.chance.coupchance.Entites.Professeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfesseurRepository extends JpaRepository<Professeur, Long> {
    List<Professeur> findByNomContainingOrPrenomContaining(String nom, String prenom);
      Optional<Professeur> findById(Long id);

      @Query("SELECT p FROM Professeur p JOIN p.matieres m WHERE m = :matiere")
      
      List<Professeur> findByMatiere(@Param("matiere") Matiere matiere);
      
   

      @EntityGraph(attributePaths = {"matieres", "classes"})
      @Query("SELECT p FROM Professeur p LEFT JOIN FETCH p.matieres LEFT JOIN FETCH p.classes")
      List<Professeur> findAllWithMatieresAndClasses();
      
    
    // Solution 2: Si vous voulez trouver les professeurs qui enseignent une matière spécifique
    @Query("SELECT p FROM Professeur p JOIN p.matieres m WHERE m.id = :matiereId")
    List<Professeur> findByMatiereId(@Param("matiereId") Long matiereId);

    @Query("SELECT p FROM Professeur p LEFT JOIN FETCH p.matieres WHERE p.id = :id")
Professeur findByIdWithMatieres(@Param("id") Long id);

}
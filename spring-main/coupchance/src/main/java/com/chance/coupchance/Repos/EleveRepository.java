package com.chance.coupchance.Repos;

import com.chance.coupchance.Entites.Classe;
import com.chance.coupchance.Entites.Eleves;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EleveRepository extends JpaRepository<Eleves, Integer> {
    List<Eleves> findByClasseId(Long classeId);
    List<Eleves> findByNomContainingOrPrenomContaining(String nom, String prenom);
    List<Eleves> findByMatricule(String matricule);
    // find by id
    Optional<Eleves> findById(Long id);
   
    List<Eleves> findByClasseNom(@Param("nom") String nom);

}
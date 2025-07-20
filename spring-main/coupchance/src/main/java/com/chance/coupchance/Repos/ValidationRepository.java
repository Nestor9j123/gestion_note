package com.chance.coupchance.Repos;

import com.chance.coupchance.Entites.User;
import com.chance.coupchance.Entites.Validation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

public interface ValidationRepository extends JpaRepository<Validation, Integer> {
    
    // Trouve une validation par code
    Optional<Validation> findByCode(String code);
    
    // Trouve une validation par utilisateur et code
    Optional<Validation> findByUserAndCode(User user, String code);
    
    // Vérifie si un code non expiré existe pour un utilisateur
    @Query("SELECT v FROM Validation v WHERE v.user = :user AND v.code = :code AND v.expiration > :now")
    Optional<Validation> findValidByUserAndCode(User user, String code, Instant now);
    
    // Supprime toutes les validations expirées
    @Transactional
    @Modifying
    @Query("DELETE FROM Validation v WHERE v.expiration <= :now")
    void deleteAllExpiredSince(Instant now);
    
    // Compte le nombre de tentatives d'activation récentes
    @Query("SELECT COUNT(v) FROM Validation v WHERE v.user = :user AND v.creation >= :since")
    long countRecentAttemptsByUser(User user, Instant since);
}
package com.chance.coupchance.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.chance.coupchance.Entites.User;
import com.chance.coupchance.Entites.Validation;
import com.chance.coupchance.Repos.ValidationRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ValidationService {
    private final ValidationRepository validationRepository;
    private final NotificationService notificationService;

    /**
     * Méthode existante - à conserver pour compatibilité
     */
    public void saveValidationCode(User user) {
        Validation validation = generateValidation(user);
        this.validationRepository.save(validation);
        // this.notificationService.sendNotification(validation);
    }

    /**
     * Nouvelle méthode avec retour de l'entité Validation
     */
    public Validation createAndSendValidationCode(User user) {
        Validation validation = generateValidation(user);
        validation = validationRepository.save(validation);
        
        /*
        try {
            notificationService.sendNotification(validation);
            return validation;
        } catch (Exception e) {
            // Rollback si l'envoi échoue
            validationRepository.delete(validation);
            throw new RuntimeException("Échec de l'envoi du code d'activation", e);
        }
        */
        return validation;
    }

    /**
     * Méthode privée commune pour générer une validation
     */
    private Validation generateValidation(User user) {
        Validation validation = new Validation();
        validation.setUser(user);
        Instant now = Instant.now();
        validation.setCreation(now);
        validation.setExpiration(now.plus(20, ChronoUnit.MINUTES));
        
        String code = String.format("%05d", new Random().nextInt(99999));
        validation.setCode(code);
        
        return validation;
    }

    public Validation getValidation(String code) {
        return validationRepository.findByCode(code)
               .orElseThrow(() -> new RuntimeException("Code de validation invalide"));
    }
}
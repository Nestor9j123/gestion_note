package com.chance.coupchance.Service;

import java.time.Instant;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;  // Assurez-vous que l'import est correct
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.chance.coupchance.Entites.Role;
import com.chance.coupchance.Entites.User;
import com.chance.coupchance.Entites.Validation;
import com.chance.coupchance.Enum.Roles;
import com.chance.coupchance.Repos.User_Repos;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Service
@Slf4j
public class UserService implements UserDetailsService {

    private final User_Repos userRepos;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ValidationService validationService;

    /**
     * Vérifie si un utilisateur existe déjà avec cet email
     */
    public boolean existsByEmail(String email) {
        return userRepos.findByEmail(email).isPresent();
    }

    /**
     * Enregistre un nouvel utilisateur
     */
    @Transactional
    public void registerUser(User user) {
        log.info("Enregistrement d'un nouvel utilisateur : {}", user.getEmail());

        // Hachage du mot de passe
        String rawPassword = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(rawPassword));
        log.info("Mot de passe avant hachage : {}", rawPassword);
        log.info("Mot de passe après hachage : {}", user.getPassword());

        user.setActif(false);  // Utilisateur non actif après l'enregistrement

        // Attribution du rôle par défaut
        Role roleUser = new Role();
        roleUser.setLibelle(Roles.USER);
        user.setRole(roleUser);

        // Sauvegarde de l'utilisateur en base de données (persist)
        userRepos.save(user);  // `save()` ou `merge()` si l'entité est détachée

        log.info("Utilisateur enregistré avec succès : {}", user.getEmail());
    }

    /**
     * Mise à jour du mot de passe
     */
    @Transactional
    public boolean updatePassword(String email, String newPassword) {
        User user = userRepos.findByEmail(email).orElse(null);
        if (user == null) {
            log.error("Utilisateur avec l'email {} non trouvé", email);
            return false;
        }

        String hashedPassword = bCryptPasswordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);
        userRepos.save(user);  // Utilisez save() ou merge() selon le cas
        log.info("Mot de passe mis à jour pour l'utilisateur : {}", email);
        return true;
    }

    /**
     * Récupère un utilisateur par son ID
     */
    public User getUserById(Long id) {
        return userRepos.findById(id).orElse(null);
    }

    /**
     * Inscription d'un utilisateur avec vérifications et envoi du code d'activation
     */
    @Transactional
    public void inscription(User user) {
        log.info("Tentative d'inscription pour : {}", user.getEmail());

        // Vérification de l'email
        if (!user.getEmail().contains("@") || !user.getEmail().contains(".")) {
            throw new RuntimeException("L'email doit contenir '@' et '.'");
        }

        // Vérification si l'email est déjà utilisé
        if (existsByEmail(user.getEmail())) {
            throw new RuntimeException("Cet email existe déjà");
        }

        // Hachage du mot de passe
        String rawPassword = user.getPassword();
        user.setPassword(bCryptPasswordEncoder.encode(rawPassword));
        log.info("Mot de passe avant hachage : {}", rawPassword);
        log.info("Mot de passe après hachage : {}", user.getPassword());

        // Attribution du rôle par défaut
        Role roleUser = new Role();
        roleUser.setLibelle(Roles.USER);
        user.setRole(roleUser);

        // Sauvegarde de l'utilisateur en base de données
        userRepos.save(user);  // `save()` ou `merge()`

        // Génération et enregistrement du code de validation
        validationService.saveValidationCode(user);

        log.info("Utilisateur inscrit avec succès : {}", user.getEmail());
    }

    /**
     * Activation d'un compte utilisateur avec un code de validation
     */
    @Transactional
    public boolean activation(Map<String, String> activationData) {
        String code = activationData.get("code");

        if (code == null || code.isEmpty()) {
            throw new RuntimeException("Le code d'activation est vide ou invalide.");
        }

        Validation validation = validationService.getValidation(code);
        if (validation == null) {
            throw new RuntimeException("Code de validation invalide.");
        }

        if (Instant.now().isAfter(validation.getExpiration())) {
            throw new RuntimeException("Code de validation expiré.");
        }

        // Activation du compte utilisateur
        User user = userRepos.findById(validation.getUser().getId())
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setActif(true);
        userRepos.save(user);  // `save()` ou `merge()`

        log.info("Activation réussie pour l'utilisateur : {}", user.getEmail());
        return true;
    }

    /**
     * Recherche d'un utilisateur par email
     */
    public User findByEmail(String email) {
        return userRepos.findByEmail(email).orElse(null);
    }

    /**
     * Chargement d'un utilisateur par son email (pour Spring Security)
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepos.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
    }
}

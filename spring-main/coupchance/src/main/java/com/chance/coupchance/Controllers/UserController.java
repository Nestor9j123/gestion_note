package com.chance.coupchance.Controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.chance.coupchance.DTO.AuthentificationDto;
import com.chance.coupchance.DTO.UserRegistrationDto;
import com.chance.coupchance.Entites.Role;
import com.chance.coupchance.Entites.User;
import com.chance.coupchance.Enum.Roles;
import com.chance.coupchance.Service.UserService;
import com.chance.coupchance.Service.ValidationService;
import com.chance.coupchance.Repos.RoleRepository;
import com.chance.coupchance.Repos.User_Repos;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@RestController
@Slf4j
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final User_Repos userRepos;
    private final ValidationService validationService;
    private final RoleRepository roleRepository;

    @GetMapping("/roles/check")
    public ResponseEntity<Boolean> checkRoleExists(@RequestParam("role") String role) {
        try {
            Roles roleEnum = Roles.valueOf(role.toUpperCase());
            boolean exists = roleRepository.findByLibelle(roleEnum).isPresent();
            return ResponseEntity.ok(exists);
        } catch (IllegalArgumentException e) {
            log.error("Rôle invalide : {}", role);
            return ResponseEntity.badRequest().body(false);
        }
    }

    /**
     * Endpoint pour créer un rôle
     */
    @PostMapping("/roles")
    public ResponseEntity<String> createRole(@RequestBody Map<String, String> roleData) {
        String roleName = roleData.get("role");
        if (roleName == null || roleName.isEmpty()) {
            return ResponseEntity.badRequest().body("Le nom du rôle est requis");
        }

        try {
            Roles roleEnum = Roles.valueOf(roleName.toUpperCase());
            if (roleRepository.findByLibelle(roleEnum).isPresent()) {
                return ResponseEntity.badRequest().body("Le rôle existe déjà");
            }

            Role role = new Role();
            role.setLibelle(roleEnum);
            roleRepository.save(role);

            return ResponseEntity.ok("Rôle créé avec succès");
        } catch (IllegalArgumentException e) {
            log.error("Rôle invalide : {}", roleName);
            return ResponseEntity.badRequest().body("Rôle invalide");
        }
    }

    /**
     * Inscription d'un utilisateur
     */
    @PostMapping(value = "/inscription", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<?> inscription(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            log.info("Tentative d'inscription pour : {}", userRegistrationDto.getEmail());

            // Validation de l'email
            if (!userRegistrationDto.getEmail().contains("@") || !userRegistrationDto.getEmail().contains(".")) {
                return ResponseEntity.badRequest().body("L'email doit contenir '@' et '.'");
            }

            // Vérification si l'email existe déjà
            if (userService.existsByEmail(userRegistrationDto.getEmail())) {
                return ResponseEntity.badRequest().body("Cet email est déjà utilisé");
            }

            // Création du nouvel utilisateur
            User user = new User();
            user.setName(userRegistrationDto.getName());
            user.setEmail(userRegistrationDto.getEmail());
            user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
            user.setActif(false); // Compte désactivé jusqu'à validation

            // Attribution du rôle
            Roles roleEnum = userRegistrationDto.isAdmin() ? Roles.ADMIN : Roles.USER;

            // Récupérer le rôle depuis la base de données
            Role role = roleRepository.findByLibelle(roleEnum)
                    .orElseThrow(() -> new IllegalArgumentException("Rôle " + roleEnum + " non trouvé"));

            user.setRole(role);

            // Sauvegarde de l'utilisateur
            user = userRepos.save(user);

            // Génération et envoi du code de validation
            // validationService.saveValidationCode(user);

            return ResponseEntity.ok("Inscription réussie. Veuillez vérifier votre email pour activer votre compte.");
        } catch (IllegalArgumentException e) {
            log.error("Erreur lors de l'inscription : {}", e.getMessage());
            return ResponseEntity.badRequest().body("Erreur lors de l'inscription : " + e.getMessage());
        } catch (Exception e) {
            log.error("Erreur interne lors de l'inscription", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur est survenue lors de l'inscription");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin-only")
    public ResponseEntity<String> adminAccess() {
        return ResponseEntity.ok("Accès réservé aux admins");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user-only")
    public ResponseEntity<String> userAccess() {
        return ResponseEntity.ok("Accès réservé aux utilisateurs");
    }


     /**
     * Activation du compte utilisateur
     */
    @PostMapping(value = "/activation", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> activation(@RequestBody Map<String, String> activationData) {
        try {


            boolean activated = userService.activation(activationData);

            if (activated) {

                return Map.of("message", "Activation réussie !");
            } else {

                return Map.of("message", "Code d'activation incorrect ou compte déjà activé.");
            }
        } catch (Exception e) {

            return Map.of("message", "Erreur interne du serveur.");
        }
    }

    @PostMapping(value = "/updatePassword", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updatePassword(@RequestBody Map<String, String> passwordData) {
        String email = passwordData.get("email");
        String newPassword = passwordData.get("newPassword");
    
        boolean isUpdated = userService.updatePassword(email, newPassword);
    
        if (isUpdated) {
            return ResponseEntity.ok("Mot de passe mis à jour");
        } else {
            return ResponseEntity.badRequest().body("L'utilisateur avec cet email n'a pas été trouvé");
        }
    }

    /**
     * Mise à jour du rôle d'un utilisateur
     */
    @PutMapping("/updateRole")
    public ResponseEntity<String> updateUserRole(@RequestBody Map<String, String> roleData) {
        String email = roleData.get("email");
        String roleStr = roleData.get("role");

        if (email == null || roleStr == null) {
            return ResponseEntity.badRequest().body("Email et rôle sont requis");
        }

        User user = userService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }

        try {
            Roles roleEnum = Roles.valueOf(roleStr.toUpperCase());
            Role newRole = roleRepository.findByLibelle(roleEnum)
                    .orElseThrow(() -> new IllegalArgumentException("Rôle " + roleEnum + " non trouvé"));

            user.setRole(newRole);
            userRepos.save(user); // Sauvegarde en base


            return ResponseEntity.ok("Rôle mis à jour avec succès !");

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body("Rôle invalide !");
        }
    }

    /**
     * Connexion d'un utilisateur
     */
    @PostMapping(value = "/connexion", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> connexion(@RequestBody AuthentificationDto connexion) {
        try {


            // Recherche de l'utilisateur par email
            User user = userService.findByEmail(connexion.email());
            if (user == null) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Email ou mot de passe incorrect."));
            }

            // Vérification du mot de passe
            boolean passwordMatches = passwordEncoder.matches(connexion.password().trim(), user.getPassword());
            if (!passwordMatches) {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Email ou mot de passe incorrect."));
            }


            return ResponseEntity.ok(Map.of(
                    "message", "Connexion réussie",
                    "role", user.getRole().getLibelle().toString()
            ));
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erreur interne du serveur."));
        }
    }


}

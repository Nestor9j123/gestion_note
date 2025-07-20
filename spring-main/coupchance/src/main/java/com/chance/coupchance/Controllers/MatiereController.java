package com.chance.coupchance.Controllers;

import com.chance.coupchance.DTO.MatiereDTO;
import com.chance.coupchance.Entites.Matiere;
import com.chance.coupchance.Entites.Professeur;
import com.chance.coupchance.Repos.MatiereRepository;
import com.chance.coupchance.Service.MatiereService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matieres")
public class MatiereController {
    private final MatiereService matiereService;
    private final MatiereRepository matiereRepository;

    public MatiereController(MatiereService matiereService, MatiereRepository matiereRepository) {
        this.matiereRepository = matiereRepository;
        this.matiereService = matiereService;
    }

    @PostMapping
    public Matiere createMatiere(@RequestBody Matiere matiere) {
        // Validation des champs college et lycee
        if (!matiere.isCollege() && !matiere.isLycee()) {
            throw new IllegalArgumentException("Au moins un niveau (Collège ou Lycée) doit être sélectionné.");
        }
        return matiereService.createMatiere(matiere);
    }

    @GetMapping
    public List<MatiereDTO> getAllMatieres() {
        return matiereRepository.findAll()
            .stream()
            .map(MatiereDTO::new)
            .collect(Collectors.toList());
    }
    @GetMapping("/niveau/{niveau}")
    public List<MatiereDTO> getMatieresByNiveau(@PathVariable String niveau) {
        return matiereRepository.findByNiveau(niveau)
            .stream()
            .map(MatiereDTO::new)
            .collect(Collectors.toList());
    }
   

    @GetMapping("/{id}")
    public MatiereDTO getMatiereById(@PathVariable Long id) {
        Matiere matiere = matiereService.getMatiereById(id);
        return new MatiereDTO(matiere);
    }



    @PutMapping("/{id}")
    public Matiere updateMatiere(@PathVariable Long id, @RequestBody Matiere matiere) {
        // Validation des champs college et lycee
        if (!matiere.isCollege() && !matiere.isLycee()) {
            throw new IllegalArgumentException("Au moins un niveau (Collège ou Lycée) doit être sélectionné.");
        }
        return matiereService.updateMatiere(id, matiere);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMatiere(@PathVariable Long id) {
        try {
            matiereService.deleteMatiere(id);
            return ResponseEntity.ok("Matière supprimée avec succès.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Matière non trouvée.");
        }
    }

    @GetMapping("/search")
    public List<MatiereDTO> searchMatieres(@RequestParam String nom) {
        return matiereService.searchMatieres(nom)
            .stream()
            .map(MatiereDTO::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/coefficient")
    public List<MatiereDTO> getMatieresByCoefficient(@RequestParam double minCoefficient) {
        return matiereService.getMatieresByCoefficientGreaterThan(minCoefficient)
            .stream()
            .map(MatiereDTO::new)
            .collect(Collectors.toList());
    }

    @GetMapping("/{id}/professeur")
    public Professeur getProfesseurByMatiere(@PathVariable Long id) {
        return matiereService.findProfesseurByMatiereId(id);
    }
}
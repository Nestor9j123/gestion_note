package com.chance.coupchance.Controllers;

import com.chance.coupchance.DTO.EleveCreateDTO;
import com.chance.coupchance.DTO.EleveDTO;
import com.chance.coupchance.Entites.Classe;
import com.chance.coupchance.Entites.Eleves;
import com.chance.coupchance.Entites.Eleves.Sexe;
import com.chance.coupchance.Repos.EleveRepository;
import com.chance.coupchance.Service.ClasseService;
import com.chance.coupchance.Service.EleveService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eleves")
public class EleveController {

    private final EleveService eleveService;
    private final EleveRepository eleveRepository;
    private final ClasseService classeService;

    public EleveController(EleveService eleveService, EleveRepository eleveRepository, ClasseService classeService) {
        this.eleveService = eleveService;
        this.eleveRepository = eleveRepository;
        this.classeService = classeService;
    }

    // CREATE ELEVE
    @PostMapping
    public ResponseEntity<EleveDTO> createEleve(@RequestBody @Validated EleveDTO eleveDTO) {
        // Utiliser le champ "classe" du DTO
 Classe classe = classeService.findByNom(eleveDTO.getClasse())
            .orElseGet(() -> {
                Classe newClasse = new Classe();
                newClasse.setNom(eleveDTO.getClasse()); // Ici on utilise bien le champ "classe"
                return classeService.createClasse(newClasse);
            });
    
        // Création de l'élève
        Eleves eleve = new Eleves();
        eleve.setMatricule(eleveDTO.getMatricule());
        eleve.setNom(eleveDTO.getNom());
        eleve.setPrenom(eleveDTO.getPrenom());
        eleve.setSexe(Sexe.valueOf(eleveDTO.getSexe()));
        eleve.setDateNaissance(eleveDTO.getDateNaissance());
        eleve.setClasse(classe);
    
        // Sauvegarde de l'élève
        Eleves savedEleve = eleveService.createEleve(eleve);
    
        // Retourne l'élève sous forme de DTO
        return ResponseEntity.ok(convertToDTO(savedEleve));
    }
    
    



    // GET ALL ELEVE (liste)
    @GetMapping
    public List<EleveDTO> getAllEleves() {
        return eleveRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET ONE ELEVE BY ID
    @GetMapping("/{id}")
    public ResponseEntity<EleveDTO> getEleveById(@PathVariable Integer id) {
        Eleves eleve = eleveService.getEleveById(id);
        return ResponseEntity.ok(convertToDTO(eleve));
    }

    @GetMapping("/{className}")
public List<EleveDTO> getElevesByClasse(@PathVariable String className) {
    return eleveRepository.findByClasseNom(className)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    // UPDATE



      @PutMapping("/{matricule}")
    public ResponseEntity<EleveDTO> updateEleveByMatricule(@PathVariable String matricule, @RequestBody @Validated EleveDTO eleveDTO) {
        // Vérifier si l'élève existe déjà
        Eleves existingEleve = eleveService.getEleveByMatricule(matricule);
        if (existingEleve == null) {
            return ResponseEntity.notFound().build();
        }
        

        // Mettre à jour les informations de l'élève
        existingEleve.setNom(eleveDTO.getNom());
        existingEleve.setPrenom(eleveDTO.getPrenom());
        existingEleve.setSexe(Sexe.valueOf(eleveDTO.getSexe()));
        existingEleve.setDateNaissance(eleveDTO.getDateNaissance());

        // Mettre à jour la classe si elle a changé
        if (!existingEleve.getClasse().getNom().equals(eleveDTO.getClasse())) {
            Classe classe = classeService.findByNom(eleveDTO.getClasse())
                    .orElseThrow(() -> new RuntimeException("Classe non trouvée"));
            existingEleve.setClasse(classe);
        }

        // Enregistrer les modifications
        Eleves updatedEleve = eleveService.updateEleve(existingEleve.getId(), existingEleve);
        return ResponseEntity.ok(convertToDTO(updatedEleve));
    }
   
    
    // DELETE
    @DeleteMapping("/{matricule}")
    public ResponseEntity<Void> deleteEleve(@PathVariable String matricule) throws Exception {
        eleveService.deleteEleve(matricule);
        return ResponseEntity.noContent().build();
    }

    // GET BY CLASSE
  /*   @GetMapping("/classe/{classeId}")
    public List<EleveDTO> getElevesByClasse(@PathVariable Long classeId) {
        return eleveService.getElevesByClasse(classeId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }*/

    @GetMapping("/classe/{className}")
public List<EleveDTO> getElevesByClasseName(@PathVariable String className) {
    return eleveRepository.findByClasseNom(className)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}

    // SEARCH
    @GetMapping("/search")
    public List<EleveDTO> searchEleves(@RequestParam String searchTerm) {
        return eleveService.searchEleves(searchTerm)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // GET BY MATRICULE
    @GetMapping("/matricule/{matricule}")
    public ResponseEntity<EleveDTO> getEleveByMatricule(@PathVariable String matricule) {
        Eleves eleve = eleveService.getEleveByMatricule(matricule);
        return ResponseEntity.ok(convertToDTO(eleve));
    }

    // Mapping utilitaire Entité -> DTO
    private EleveDTO convertToDTO(Eleves eleve) {
        EleveDTO dto = new EleveDTO();
        dto.setMatricule(eleve.getMatricule());
        dto.setNom(eleve.getNom());
        dto.setPrenom(eleve.getPrenom());
        dto.setSexe(eleve.getSexe() != null ? eleve.getSexe().toString() : null);
        dto.setDateNaissance(eleve.getDateNaissance());
        dto.setId(eleve.getId());
        // Log pour vérifier la classe
        if (eleve.getClasse() != null) {
            System.out.println("Classe Nom: " + eleve.getClasse().getNom());
            dto.setClasse(eleve.getClasse().getNom());
        } else {
            System.out.println("Classe est null pour l'élève " + eleve.getMatricule());
        }
        return dto;
    }
    
    
}

package com.chance.coupchance.Controllers;

import com.chance.coupchance.DTO.ClasseDTO;
import com.chance.coupchance.DTO.MatiereDTO;
import com.chance.coupchance.DTO.MatieresDTO;
import com.chance.coupchance.DTO.ProfesseurDTO;
import com.chance.coupchance.Entites.Classe;
import com.chance.coupchance.Entites.Matiere;
import com.chance.coupchance.Entites.Professeur;
import com.chance.coupchance.Repos.ProfesseurRepository;
import com.chance.coupchance.Service.ProfesseurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/professeurs")
public class ProfesseurController {
    private final ProfesseurService professeurService;
    private final ProfesseurRepository professeurRepository;

    public ProfesseurController(ProfesseurService professeurService, ProfesseurRepository professeurRepository) {
        this.professeurService = professeurService;
        this.professeurRepository = professeurRepository;
    }


    @PostMapping
    public Professeur createProfesseur(@RequestBody ProfesseurDTO dto) {
        Professeur professeur = new Professeur();
        professeur.setNom(dto.nom);
        professeur.setPrenom(dto.prenom);

        // Conversion des MatieresDTO vers entités Matiere
        Set<Matiere> matieres = dto.matieres.stream()
                .map(matieresDTO -> {
                    Matiere matiere = new Matiere();
                    matiere.setNom(matieresDTO.nom);
                    matiere.setCoefficient(matieresDTO.coefficient);
                    matiere.setCollege(matieresDTO.college);
                    matiere.setLycee(matieresDTO.lycee);
                    matiere.setProfesseur(professeur);
                    return matiere;
                })

                .collect(Collectors.toSet());
        professeur.setMatieres(matieres);

        // Conversion des noms de classes vers entités Classe
        Set<Classe> classes = dto.classes.stream()
                .map(nomClasse -> {
                    Classe classe = new Classe();
                    classe.setNom(nomClasse);
                    classe.setProfesseur(professeur);
                    return classe;
                })
                .collect(Collectors.toSet());
        professeur.setClasses(classes);

        return professeurRepository.save(professeur);
    }
    


 

   @GetMapping
public ResponseEntity<List<ProfesseurDTO>> getAllProfesseursWithDetails() {
    List<Professeur> professeurs = professeurService.getAllProfesseurs();
    
    List<ProfesseurDTO> professeurDTOs = professeurs.stream()
        .map(professeur -> {
            ProfesseurDTO dto = new ProfesseurDTO();
            dto.setNom(professeur.getNom());
            dto.setPrenom(professeur.getPrenom());
            dto.setId(professeur.getId());


            // Conversion des matières avec MatieresDTO (seulement le nom de la matière)
            if (professeur.getMatieres() != null) {
                List<MatieresDTO> matieresDTO = professeur.getMatieres().stream()
                    .map(matiere -> {
                        MatieresDTO matieresDTOItem = new MatieresDTO();
                        matieresDTOItem.nom = matiere.getNom(); // uniquement le nom de la matière
                        return matieresDTOItem;
                    })
                    .collect(Collectors.toList());
                dto.setMatieres(matieresDTO);
            }

            // Conversion des classes en List<String> (juste les noms)
            if (professeur.getClasses() != null) {
                List<String> nomsClasses = professeur.getClasses().stream()
                    .map(Classe::getNom)
                    .collect(Collectors.toList());
                dto.setClasses(nomsClasses);
            }

            return dto;
        })
        .collect(Collectors.toList());

    return ResponseEntity.ok(professeurDTOs);
}

    @GetMapping("/{id}")
    public Professeur getProfesseurById(@PathVariable Long id) {
        return professeurService.getProfesseurById(id);
    }

    @PutMapping("/{id}")
    public Professeur updateProfesseur(@PathVariable Long id, @RequestBody Professeur professeur) {
        return professeurService.updateProfesseur(id, professeur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfesseur(@PathVariable Long id) {
        professeurService.deleteProfesseur(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public List<Professeur> searchProfesseurs(@RequestParam String searchTerm) {
        return professeurService.searchProfesseurs(searchTerm);
    }

    @GetMapping("/{id}/matieres")
    public Set<Matiere> getMatieresByProfesseur(@PathVariable Long id) {
        return professeurService.getMatieresByProfesseur(id);
    }
}
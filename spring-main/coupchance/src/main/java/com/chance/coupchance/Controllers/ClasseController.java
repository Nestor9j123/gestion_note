package com.chance.coupchance.Controllers;

import com.chance.coupchance.DTO.ClasseDTO;
import com.chance.coupchance.Entites.Classe;
import com.chance.coupchance.Service.ClasseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClasseController {
    private final ClasseService classeService;

    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @PostMapping
    public ResponseEntity<ClasseDTO> createClasse(@RequestBody ClasseDTO dto) {
        Classe classe = new Classe();
        classe.setNom(dto.getNom());
        classe.setEffectif(dto.getEffectif());
    
        Classe savedClasse = classeService.createClasse(classe);
        ClasseDTO response = new ClasseDTO(savedClasse);
        return ResponseEntity.ok(response);
    }
    

@GetMapping
public ResponseEntity<List<ClasseDTO>> getAllClasses() {
    List<Classe> classes = classeService.getAllClasses();
    List<ClasseDTO> dtos = classes.stream()
        .map(ClasseDTO::new)
        .toList();

    return ResponseEntity.ok(dtos);
}


    @GetMapping("/{id}")
    public Classe getClasseById(@PathVariable Long id) {
        return classeService.getClasseById(id);
    }

    @PutMapping("/{id}")
    public Classe updateClasse(@PathVariable Long id, @RequestBody Classe classe) {
        return classeService.updateClasse(id, classe);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClasse(@PathVariable Long id) {
        classeService.deleteClasse(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/effectif/{effectif}")
    public List<Classe> getClassesByEffectif(@PathVariable int effectif) {
        return classeService.getClassesByEffectif(effectif);
    }

    @GetMapping("/search")
    public List<Classe> searchClassesByName(@RequestParam String nom) {
        return classeService.searchClassesByName(nom);
    }

    @GetMapping("/{id}/effectif")
    public ResponseEntity<Integer> getEffectifByClasseId(@PathVariable Long id) {
        Classe classe = classeService.getClasseById(id);
        int effectif = 0;
        if (classe.getEleves() != null) {
            effectif = classe.getEleves().size();
        } else {
            effectif = classe.getEffectif();
        }
        return ResponseEntity.ok(effectif);
    }
}
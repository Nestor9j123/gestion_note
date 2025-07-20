package com.chance.coupchance.Service;

import com.chance.coupchance.Entites.Classe;
import com.chance.coupchance.Repos.ClasseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClasseService {
    private final ClasseRepository classeRepository;

    public ClasseService(ClasseRepository classeRepository) {
        this.classeRepository = classeRepository;
    }

    public Classe createClasse(Classe classe) {
        return classeRepository.save(classe);
    }

    public List<Classe> getAllClasses() {
        return classeRepository.findAllWithEleves();
    }
    

    public Classe getClasseById(Long id) {
        return classeRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Classe non trouvée avec l'id: " + id));
    }

    public Classe updateClasse(Long id, Classe classeDetails) {
        Classe classe = getClasseById(id);
        classe.setNom(classeDetails.getNom());
        classe.setEffectif(classeDetails.getEffectif());
        return classeRepository.save(classe);
    }

    public void deleteClasse(Long id) {
        Classe classe = getClasseById(id);
        classeRepository.delete(classe);
    }

    public List<Classe> getClassesByEffectif(int effectif) {
        return classeRepository.findByEffectif(effectif);
        
    }
  

    public Optional<Classe> findByNom(String nom) {
        return classeRepository.findByNom(nom).stream().findFirst();
    }

    public List<Classe> searchClassesByName(String nom) {
        return classeRepository.findByNomContaining(nom);
    }
}
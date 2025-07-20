package com.chance.coupchance.Service;

import com.chance.coupchance.Entites.Eleves;
import com.chance.coupchance.Repos.EleveRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EleveService {
    private final EleveRepository eleveRepository;

    public EleveService(EleveRepository eleveRepository) {
        this.eleveRepository = eleveRepository;
    }

    public Eleves createEleve(Eleves eleve) {
        return eleveRepository.save(eleve);
    }

    public List<Eleves> getAllEleves() {
        return eleveRepository.findAll();
    }

    public Eleves getEleveById(Integer id) {
        return eleveRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Élève non trouvé avec l'id: " + id));
    }

    public Eleves updateEleve(Integer id, Eleves eleveDetails) {
        Eleves eleve = getEleveById(id);
        System.out.println("Avant mise à jour : " + eleve); // Log pour voir les valeurs avant
        eleve.setNom(eleveDetails.getNom());
        eleve.setPrenom(eleveDetails.getPrenom());
        eleve.setDateNaissance(eleveDetails.getDateNaissance());
        eleve.setMatricule(eleveDetails.getMatricule());
        eleve.setSexe(eleveDetails.getSexe());
        eleve.setClasse(eleveDetails.getClasse());
        System.out.println("Après mise à jour : " + eleve); // Log pour voir les valeurs après
        return eleveRepository.save(eleve);
    }
    

    public void deleteEleve(String matricule) throws Exception {
        Eleves eleve = (Eleves) eleveRepository.findByMatricule(matricule).stream()
                .findFirst()
                .orElse(null);
        if (eleve == null) {
            throw new Exception("Élève non trouvé");
        }
        eleveRepository.delete(eleve);
    }
    

    public List<Eleves> getElevesByClasse(Long classeId) {
        return eleveRepository.findByClasseId(classeId);
    }

    public List<Eleves> searchEleves(String searchTerm) {
        return eleveRepository.findByNomContainingOrPrenomContaining(searchTerm, searchTerm);
    }

    public Eleves getEleveByMatricule(String matricule) {
        return eleveRepository.findByMatricule(matricule).stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec le matricule: " + matricule));
    }
}
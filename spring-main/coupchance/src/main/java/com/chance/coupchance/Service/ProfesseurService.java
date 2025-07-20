package com.chance.coupchance.Service;

import com.chance.coupchance.Entites.Matiere;
import com.chance.coupchance.Entites.Professeur;
import com.chance.coupchance.Repos.MatiereRepository;
import com.chance.coupchance.Repos.ProfesseurRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProfesseurService {
    private final ProfesseurRepository professeurRepository;
    private final MatiereRepository matiereRepository;

    public ProfesseurService(ProfesseurRepository professeurRepository, MatiereRepository matiereRepository) {
        this.professeurRepository = professeurRepository;
        this.matiereRepository = matiereRepository;
    }

    public Professeur createProfesseur(Professeur professeur) {
        return professeurRepository.save(professeur);
    }

    @Transactional
    public List<Professeur> getAllProfesseurs() {
        return professeurRepository.findAllWithMatieresAndClasses();
    }

    public Professeur getProfesseurById(Long id) {
        return professeurRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Professeur non trouvé avec l'id: " + id));
    }

    public Professeur updateProfesseur(Long id, Professeur professeurDetails) {
        Professeur professeur = getProfesseurById(id);
        professeur.setNom(professeurDetails.getNom());
        professeur.setPrenom(professeurDetails.getPrenom());
        
        // Mise à jour de la liste des matières
        if(professeurDetails.getMatieres() != null) {
            // D'abord retirer l'ancienne association
            if(professeur.getMatieres() != null) {
                for(Matiere matiere : professeur.getMatieres()) {
                    matiere.setProfesseur(null);
                }
            }
            
            // Puis ajouter les nouvelles associations
            professeur.setMatieres(professeurDetails.getMatieres());
            if(professeur.getMatieres() != null) {
                for(Matiere matiere : professeur.getMatieres()) {
                    matiere.setProfesseur(professeur);
                }
            }
        }
        
        return professeurRepository.save(professeur);
    }
    public void deleteProfesseur(Long id) {
        Professeur professeur = getProfesseurById(id);
        professeurRepository.delete(professeur);
    }

    public List<Professeur> searchProfesseurs(String searchTerm) {
        return professeurRepository.findByNomContainingOrPrenomContaining(searchTerm, searchTerm);
    }

    
    public Set<Matiere> getMatieresByProfesseur(Long professeurId) {
        Professeur professeur = getProfesseurById(professeurId);
        return professeur.getMatieres(); // Supposant que vous avez chargé les matières en eager ou que vous utilisez @EntityGraph
    }
    public List<Professeur> getProfesseursByMatiere(Long matiereId) {
        return professeurRepository.findByMatiereId(matiereId);
    }
}
package com.chance.coupchance.Service;

import com.chance.coupchance.Entites.Matiere;
import com.chance.coupchance.Entites.Professeur;
import com.chance.coupchance.Repos.MatiereRepository;
import com.chance.coupchance.Repos.ProfesseurRepository;

import jakarta.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatiereService {
    private final MatiereRepository matiereRepository;
    private final ProfesseurRepository professeurRepository;

    public MatiereService(MatiereRepository matiereRepository, ProfesseurRepository professeurRepository) {
        this.professeurRepository = professeurRepository;
        this.matiereRepository = matiereRepository;
    }

    public Matiere createMatiere(Matiere matiere) {
        return matiereRepository.save(matiere);
    }

    public List<Matiere> getAllMatieres() {
        return matiereRepository.findAll();
    }

    public Matiere getMatiereById(Long id) {
        return matiereRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Matière non trouvée avec l'id: " + id));
    }

    public Matiere updateMatiere(Long id, Matiere matiereDetails) {
        Matiere matiere = getMatiereById(id);
        matiere.setNom(matiereDetails.getNom());
        matiere.setCoefficient(matiereDetails.getCoefficient());
        return matiereRepository.save(matiere);
    }

 
    @Transactional
    public void deleteMatiere(Long id) {
        // 1. Récupérer la matière
        Matiere matiere = matiereRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matière non trouvée"));
    
        // 2. Si elle a un professeur, on la retire proprement
        if (matiere.getProfesseur() != null) {
            Professeur professeur = matiere.getProfesseur();
            professeur.getMatieres().remove(matiere); // côté propriétaire
    
            matiere.setProfesseur(null); // côté inverse
            matiereRepository.save(matiere); // très important
            professeurRepository.save(professeur); // si tu veux être 100% propre
        }
    
        // 3. Supprimer la matière maintenant qu'elle est "orpheline"
        matiereRepository.delete(matiere);
    }
    
    
    
    
    
    
    
    

    public List<Matiere> searchMatieres(String nom) {
        return matiereRepository.findByNomContaining(nom);
    }

    public List<Matiere> getMatieresByCoefficientGreaterThan(double coefficient) {
        return matiereRepository.findByCoefficientGreaterThan(coefficient);
    }

     public Professeur findProfesseurByMatiereId(Long matiereId) {
        return matiereRepository.findProfesseurByMatiereId(matiereId)
        .orElseThrow(() -> new RuntimeException("Matière non trouvée avec l'id: " + matiereId));
    }
}
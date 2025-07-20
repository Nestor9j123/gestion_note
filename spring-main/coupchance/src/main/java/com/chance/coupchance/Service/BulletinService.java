package com.chance.coupchance.Service;

import com.chance.coupchance.DTO.BulletinDTO;
import com.chance.coupchance.Entites.Bulletin;
import com.chance.coupchance.Entites.Note;
import com.chance.coupchance.Entites.Matiere;
import com.chance.coupchance.Entites.Eleves;
import com.chance.coupchance.Repos.BulletinRepository;
import com.chance.coupchance.Repos.EleveRepository;
import com.chance.coupchance.Repos.MatiereRepository;
import com.chance.coupchance.Repos.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BulletinService {
    private final BulletinRepository bulletinRepository;

    @Autowired
    private EleveRepository eleveRepository;
    @Autowired
    private MatiereRepository matiereRepository;
    @Autowired
    private NoteRepository noteRepository;

    public BulletinService(BulletinRepository bulletinRepository) {
        this.bulletinRepository = bulletinRepository;
    }

    public Bulletin createBulletin(Bulletin bulletin) {
        return bulletinRepository.save(bulletin);
    }

    public Bulletin createBulletinFromDTO(BulletinDTO dto) {
        if (dto.getEleveId() == null) {
            throw new IllegalArgumentException("eleveId (identifiant élève) manquant dans le bulletin envoyé.");
        }
        Bulletin bulletin = new Bulletin();
        bulletin.setTrimestre(dto.getTrimestre()); // mapping direct
        bulletin.setAnneeScolaire(dto.getAnneeScolaire());
        bulletin.setClasse(dto.getClasse());
        bulletin.setDateBulletin(dto.getDateBulletin());
        bulletin.setMoyenne(dto.getMoyenne());
        bulletin.setRang(dto.getRang());
        bulletin.setMention(dto.getMention());
        bulletin.setRetards(dto.getRetards());
        bulletin.setAbsences(dto.getAbsences());
        bulletin.setPunitions(dto.getPunitions());
        bulletin.setExclusions(dto.getExclusions());
        bulletin.setHonneur(dto.isHonneur());
        bulletin.setFelicitations(dto.isFelicitations());
        bulletin.setEncouragements(dto.isEncouragements());
        bulletin.setAvertissement(dto.isAvertissement());
        bulletin.setBlame(dto.isBlame());
        bulletin.setTravail(dto.isTravail());
        bulletin.setDiscipline(dto.isDiscipline());
        bulletin.setTravailBlame(dto.isTravailBlame());
        bulletin.setDisciplineBlame(dto.isDisciplineBlame());
        bulletin.setObservations(dto.getObservations());
        // Suppression de l'appel à getEffectif()
        Eleves eleve = eleveRepository.findById(dto.getEleveId().intValue()).orElseThrow();
        bulletin.setEleve(eleve);
        // Mapping des notes
        List<Note> notes = new java.util.ArrayList<>();
        if (dto.getNotes() != null) {
            for (BulletinDTO.NoteDTO noteDTO : dto.getNotes()) {
                Note note = new Note();
                note.setId(null); // Toujours null pour une nouvelle note
                note.setTrimestre(noteDTO.getTrimestre()); // mapping du trimestre
                note.setValeur(noteDTO.getValeur());
                note.setEleve(eleve);
                note.setMatiere(matiereRepository.findById(noteDTO.getMatiereId()).orElseThrow());
                note.setBulletin(bulletin); // Lier la note au bulletin
                // Champs supplémentaires
                note.setCoefficient(noteDTO.getCoefficient() != null ? noteDTO.getCoefficient() : 1.0);
                note.setNote1(noteDTO.getNote1() != null ? noteDTO.getNote1() : 0.0);
                note.setNote2(noteDTO.getNote2() != null ? noteDTO.getNote2() : 0.0);
                note.setClassAverage(noteDTO.getClassAverage() != null ? noteDTO.getClassAverage() : 0.0);
                note.setComposition(noteDTO.getComposition() != null ? noteDTO.getComposition() : 0.0);
                note.setRank(noteDTO.getRank() != null ? noteDTO.getRank() : "");
                note.setTeacher(noteDTO.getTeacher() != null ? noteDTO.getTeacher() : "");
                note.setTermAverage(noteDTO.getTermAverage() != null ? noteDTO.getTermAverage() : 0.0);
                note.setObservation(noteDTO.getObservations());
                notes.add(note);
            }
        }
        bulletin.setNotes(notes);
        return bulletinRepository.save(bulletin);
    }

    public List<Bulletin> getAllBulletins() {
        return bulletinRepository.findAll();
    }

    public Bulletin getBulletinById(Long id) {
        return bulletinRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Bulletin non trouvé avec l'id: " + id));
    }

    public Bulletin updateBulletin(Long id, Bulletin bulletinDetails) {
        Bulletin bulletin = getBulletinById(id);
        bulletin.setMoyenne(bulletinDetails.getMoyenne());
        bulletin.setRang(bulletinDetails.getRang());
        bulletin.setTrimestre(bulletinDetails.getTrimestre());
        bulletin.setEleve(bulletinDetails.getEleve());
        return bulletinRepository.save(bulletin);
    }

    public void deleteBulletin(Long id) {
        Bulletin bulletin = getBulletinById(id);
        bulletinRepository.delete(bulletin);
    }

    public List<Bulletin> getBulletinsByEleve(Long eleveId) {
        return bulletinRepository.findByEleveId(eleveId);
    }

    public List<Bulletin> getBulletinsByTrimestre(int trimestre) {
        return bulletinRepository.findByTrimestre(trimestre);
    }

    public List<Bulletin> getBulletinsByEleveAndTrimestre(Long eleveId, int trimestre) {
        return bulletinRepository.findByEleveIdAndTrimestre(eleveId, trimestre);
    }
}
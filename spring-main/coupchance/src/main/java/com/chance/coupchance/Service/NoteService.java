package com.chance.coupchance.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chance.coupchance.DTO.NoteSaisieGroupeeDTO;
import com.chance.coupchance.Entites.Eleves;
import com.chance.coupchance.Entites.Matiere;
import com.chance.coupchance.Entites.Note;
import com.chance.coupchance.Repos.EleveRepository;
import com.chance.coupchance.Repos.MatiereRepository;
import com.chance.coupchance.Repos.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import java.util.List;

@Service
public class NoteService {
    private final NoteRepository noteRepository;
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);

    @Autowired
    private EleveRepository eleveRepository;
    @Autowired
    private MatiereRepository matiereRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }

    public Note getNoteById(Long id) {
        return noteRepository.findById(id).orElseThrow(() -> 
            new RuntimeException("Note non trouvée avec l'id: " + id));
    }

    public Note updateNote(Long id, Note noteDetails) {
        Note note = getNoteById(id);
        note.setValeur(noteDetails.getValeur());
        note.setTrimestre(noteDetails.getTrimestre());
        note.setEleve(noteDetails.getEleve());
        note.setMatiere(noteDetails.getMatiere());
        note.setCoefficient(noteDetails.getCoefficient());
        note.setNote1(noteDetails.getNote1());
        note.setNote2(noteDetails.getNote2());
        note.setClassAverage(noteDetails.getClassAverage());
        note.setComposition(noteDetails.getComposition());
        note.setRank(noteDetails.getRank());
        note.setTeacher(noteDetails.getTeacher());
        note.setTermAverage(noteDetails.getTermAverage());
        note.setObservation(noteDetails.getObservation());
        return noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        Note note = getNoteById(id);
        noteRepository.delete(note);
    }

    public List<Note> getNotesByEleve(Long eleveId) {
        return noteRepository.findByEleveId(eleveId);
    }

    public List<Note> getNotesByMatiere(Long matiereId) {
        return noteRepository.findByMatiereId(matiereId);
    }

    public List<Note> getNotesByEleveAndMatiere(Long eleveId, Long matiereId) {
        return noteRepository.findByEleveIdAndMatiereId(eleveId, matiereId);
    }

    public List<Note> getNotesByTrimestre(int trimestre) {
        return noteRepository.findByTrimestre(trimestre);
    }

 

    public void createNotesBatch(NoteSaisieGroupeeDTO batchDto) {
        Optional<Matiere> matiereOpt = matiereRepository.findById(batchDto.getIdMatiere());
        if (!matiereOpt.isPresent()) return;
        Matiere matiere = matiereOpt.get();
        int trimestre = batchDto.getTrimestre();
        for (NoteSaisieGroupeeDTO.NoteEleveDTO noteEleve : batchDto.getNotes()) {
            Optional<Eleves> eleveOpt = eleveRepository.findById(noteEleve.getIdEleve());
            if (!eleveOpt.isPresent()) continue;
            Eleves eleve = eleveOpt.get();
            // Note 1
            if (noteEleve.getNote1() != null) {
                Note note1 = new Note();
                note1.setEleve(eleve);
                note1.setMatiere(matiere);
                note1.setTrimestre(trimestre);
                note1.setValeur(noteEleve.getNote1());
                note1.setType("note1");
                noteRepository.save(note1);
            }
            // Note 2
            if (noteEleve.getNote2() != null) {
                Note note2 = new Note();
                note2.setEleve(eleve);
                note2.setMatiere(matiere);
                note2.setTrimestre(trimestre);
                note2.setValeur(noteEleve.getNote2());
                note2.setType("note2");
                noteRepository.save(note2);
            }
            // Compo
            if (noteEleve.getCompo() != null) {
                Note noteCompo = new Note();
                noteCompo.setEleve(eleve);
                noteCompo.setMatiere(matiere);
                noteCompo.setTrimestre(trimestre);
                noteCompo.setValeur(noteEleve.getCompo());
                noteCompo.setType("compo");
                noteRepository.save(noteCompo);
            }
        }
    }
}
package com.chance.coupchance.Controllers;

import com.chance.coupchance.Entites.Note;
import com.chance.coupchance.Service.NoteService;
import com.chance.coupchance.DTO.NoteSaisieGroupeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public Note createNote(@RequestBody Note note) {
        return noteService.createNote(note);
    }

    @PostMapping("/batch")
    public ResponseEntity<?> createNotesBatch(@RequestBody NoteSaisieGroupeeDTO notesDto) {
        System.out.println("Appel /api/notes/batch");
        System.out.println("DTO reçu : " + notesDto);
        try {
            noteService.createNotesBatch(notesDto);
            System.out.println("Traitement terminé sans exception");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur backend : " + e.getMessage());
        }
    }

    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }

    @GetMapping("/{id}")
    public Note getNoteById(@PathVariable Long id) {
        return noteService.getNoteById(id);
    }

    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id, @RequestBody Note note) {
        return noteService.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/eleve/{eleveId}")
    public List<Note> getNotesByEleve(@PathVariable Long eleveId) {
        return noteService.getNotesByEleve(eleveId);
    }

    @GetMapping("/matiere/{matiereId}")
    public List<Note> getNotesByMatiere(@PathVariable Long matiereId) {
        return noteService.getNotesByMatiere(matiereId);
    }

    @GetMapping("/eleve/{eleveId}/matiere/{matiereId}")
    public List<Note> getNotesByEleveAndMatiere(
            @PathVariable Long eleveId, 
            @PathVariable Long matiereId) {
        return noteService.getNotesByEleveAndMatiere(eleveId, matiereId);
    }

    @GetMapping("/trimestre/{trimestre}")
    public List<Note> getNotesByTrimestre(@PathVariable int trimestre) {
        return noteService.getNotesByTrimestre(trimestre);
    }
}
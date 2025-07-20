package com.chance.coupchance.Repos;

import com.chance.coupchance.Entites.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByEleveId(Long eleveId);
    List<Note> findByMatiereId(Long matiereId);
    List<Note> findByEleveIdAndMatiereId(Long eleveId, Long matiereId);
    List<Note> findByTrimestre(int trimestre);
    boolean existsByEleveIdAndMatiereIdAndTrimestreAndType(Long eleveId, Long matiereId, Integer trimestre, String type); 
}
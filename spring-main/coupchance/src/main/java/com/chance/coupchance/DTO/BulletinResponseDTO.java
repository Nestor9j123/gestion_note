package com.chance.coupchance.DTO;

import com.chance.coupchance.Entites.Bulletin;
import com.chance.coupchance.Entites.Note;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BulletinResponseDTO {
    private Long id;
    private int trimestre;
    public int getTrimestre() { return trimestre; }
    public void setTrimestre(int trimestre) { this.trimestre = trimestre; }
    private String anneeScolaire;
    private String classe;
    private LocalDate dateBulletin;
    private double moyenne;
    private double moyenneGenerale;
    private String rang;
    private String mention;
    private int retards;
    private int absences;
    private int punitions;
    private int exclusions;
    private boolean honneur;
    private boolean felicitations;
    private boolean encouragements;
    private boolean avertissement;
    private boolean blame;
    private boolean travail;
    private boolean discipline;
    private boolean travailBlame;
    private boolean disciplineBlame;
    private String observations;
    // Champs supplémentaires pour compatibilité front
    private double moyenneTrim1;
    private String rangTrim1;
    private double moyenneTrim2;
    private String rangTrim2;
    private double moyenneTrim3;
    private String rangTrim3;
    private double moyenneAnnuelle;
    private String rangAnnuelle;
    private double highestClassAvg;
    private double lowestClassAvg;
    private double classTrimAvg;
    private double classAnnualAvg;
    private String profTitulaire;
    // Informations de l'élève
    private String nom;
    private String prenom;
    private String matricule;
    private String sexe;
    private LocalDate dateNaissance;
    private Long eleveId;
    // Notes
    private List<NoteResponseDTO> notes;

    public static BulletinResponseDTO fromEntity(Bulletin bulletin) {
        BulletinResponseDTO dto = new BulletinResponseDTO();
        dto.setId(bulletin.getId());
        dto.setTrimestre(bulletin.getTrimestre()); // mapping du trimestre
        dto.setAnneeScolaire(bulletin.getAnneeScolaire());
        dto.setClasse(bulletin.getClasse());
        dto.setDateBulletin(bulletin.getDateBulletin());
        dto.setMoyenne(bulletin.getMoyenne());
        dto.setMoyenneGenerale(bulletin.getMoyenne());
        dto.setRang(String.valueOf(bulletin.getRang()));
        dto.setMention(bulletin.getMention());
        dto.setRetards(bulletin.getRetards());
        dto.setAbsences(bulletin.getAbsences());
        dto.setPunitions(bulletin.getPunitions());
        dto.setExclusions(bulletin.getExclusions());
        dto.setHonneur(bulletin.isHonneur());
        dto.setFelicitations(bulletin.isFelicitations());
        dto.setEncouragements(bulletin.isEncouragements());
        dto.setAvertissement(bulletin.isAvertissement());
        dto.setBlame(bulletin.isBlame());
        dto.setTravail(bulletin.isTravail());
        dto.setDiscipline(bulletin.isDiscipline());
        dto.setTravailBlame(bulletin.isTravailBlame());
        dto.setDisciplineBlame(bulletin.isDisciplineBlame());
        dto.setObservations(bulletin.getObservations());
        // Champs supplémentaires (à adapter selon l'entité Bulletin)
        dto.setMoyenneTrim1(0.0);
        dto.setRangTrim1("");
        dto.setMoyenneTrim2(0.0);
        dto.setRangTrim2("");
        dto.setMoyenneTrim3(0.0);
        dto.setRangTrim3("");
        dto.setMoyenneAnnuelle(0.0);
        dto.setRangAnnuelle("");
        dto.setHighestClassAvg(0.0);
        dto.setLowestClassAvg(0.0);
        dto.setClassTrimAvg(0.0);
        dto.setClassAnnualAvg(0.0);
        dto.setProfTitulaire("");
        // Informations de l'élève
        if (bulletin.getEleve() != null) {
            dto.setNom(bulletin.getEleve().getNom());
            dto.setPrenom(bulletin.getEleve().getPrenom());
            dto.setMatricule(bulletin.getEleve().getMatricule());
            dto.setSexe(bulletin.getEleve().getSexe() != null ? bulletin.getEleve().getSexe().toString() : null);
            dto.setDateNaissance(bulletin.getEleve().getDateNaissance());
            dto.setEleveId((long) bulletin.getEleve().getId());
        }
        // Notes
        if (bulletin.getNotes() != null) {
            dto.setNotes(bulletin.getNotes().stream()
                .map(NoteResponseDTO::fromEntity)
                .collect(Collectors.toList()));
        }
        // Log pour debug
        System.out.println("[DTO] BulletinResponseDTO créé : " + dto);
        return dto;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoteResponseDTO {
        private Long id;
        private int trimestre;
        private double valeur;
        private String type;
        private Integer absences;
        private Integer punitions;
        private Integer exclusions;
        private String observation;
        private String matiereNom;
        private Long matiereId;
        // Champs pour correspondre au frontend
        private String subject;
        private double coefficient;
        private double note1;
        private double note2;
        private double classAverage;
        private double composition;
        private double termAverage;
        private String rank;
        private String observations;
        private String teacher;
        public static NoteResponseDTO fromEntity(Note note) {
            NoteResponseDTO dto = new NoteResponseDTO();
            dto.setId(note.getId());
            dto.setTrimestre(note.getTrimestre());
            dto.setValeur(note.getValeur());
            dto.setType(note.getType());
            dto.setAbsences(note.getAbsences());
            dto.setPunitions(note.getPunitions());
            dto.setExclusions(note.getExclusions());
            dto.setObservation(note.getObservation());
            if (note.getMatiere() != null) {
                dto.setMatiereNom(note.getMatiere().getNom());
                dto.setMatiereId(note.getMatiere().getId());
                dto.setSubject(note.getMatiere().getNom());
            }
            dto.setTermAverage(note.getValeur());
            dto.setObservations(note.getObservation());
            dto.setCoefficient(note.getCoefficient() != null ? note.getCoefficient() : 1.0);
            dto.setNote1(note.getNote1() != null ? note.getNote1() : 0.0);
            dto.setNote2(note.getNote2() != null ? note.getNote2() : 0.0);
            dto.setClassAverage(note.getClassAverage() != null ? note.getClassAverage() : 0.0);
            dto.setComposition(note.getComposition() != null ? note.getComposition() : 0.0);
            dto.setRank(note.getRank() != null ? note.getRank() : "");
            dto.setTeacher(note.getTeacher() != null ? note.getTeacher() : "");
            System.out.println("[DTO] NoteResponseDTO créé : " + dto);
            return dto;
        }
    }
} 
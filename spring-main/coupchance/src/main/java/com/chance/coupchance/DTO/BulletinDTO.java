package com.chance.coupchance.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.List;

public class BulletinDTO {
    private String anneeScolaire;
    private int trimestre;
    private String classe;
    private LocalDate dateBulletin;
    private double moyenne;
    private int rang;
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
    private Long eleveId;
    private List<NoteDTO> notes;

    public BulletinDTO() {}

    // Getters et setters
    public String getAnneeScolaire() { return anneeScolaire; }
    public void setAnneeScolaire(String anneeScolaire) { this.anneeScolaire = anneeScolaire; }
    public int getTrimestre() { return trimestre; }
    public void setTrimestre(int trimestre) { this.trimestre = trimestre; }
    public String getClasse() { return classe; }
    public void setClasse(String classe) { this.classe = classe; }
    public LocalDate getDateBulletin() { return dateBulletin; }
    public void setDateBulletin(LocalDate dateBulletin) { this.dateBulletin = dateBulletin; }
    public double getMoyenne() { return moyenne; }
    public void setMoyenne(double moyenne) { this.moyenne = moyenne; }
    public int getRang() { return rang; }
    public void setRang(int rang) { this.rang = rang; }
    public String getMention() { return mention; }
    public void setMention(String mention) { this.mention = mention; }
    public int getRetards() { return retards; }
    public void setRetards(int retards) { this.retards = retards; }
    public int getAbsences() { return absences; }
    public void setAbsences(int absences) { this.absences = absences; }
    public int getPunitions() { return punitions; }
    public void setPunitions(int punitions) { this.punitions = punitions; }
    public int getExclusions() { return exclusions; }
    public void setExclusions(int exclusions) { this.exclusions = exclusions; }
    public boolean isHonneur() { return honneur; }
    public void setHonneur(boolean honneur) { this.honneur = honneur; }
    public boolean isFelicitations() { return felicitations; }
    public void setFelicitations(boolean felicitations) { this.felicitations = felicitations; }
    public boolean isEncouragements() { return encouragements; }
    public void setEncouragements(boolean encouragements) { this.encouragements = encouragements; }
    public boolean isAvertissement() { return avertissement; }
    public void setAvertissement(boolean avertissement) { this.avertissement = avertissement; }
    public boolean isBlame() { return blame; }
    public void setBlame(boolean blame) { this.blame = blame; }
    public boolean isTravail() { return travail; }
    public void setTravail(boolean travail) { this.travail = travail; }
    public boolean isDiscipline() { return discipline; }
    public void setDiscipline(boolean discipline) { this.discipline = discipline; }
    public boolean isTravailBlame() { return travailBlame; }
    public void setTravailBlame(boolean travailBlame) { this.travailBlame = travailBlame; }
    public boolean isDisciplineBlame() { return disciplineBlame; }
    public void setDisciplineBlame(boolean disciplineBlame) { this.disciplineBlame = disciplineBlame; }
    public String getObservations() { return observations; }
    public void setObservations(String observations) { this.observations = observations; }
    public Long getEleveId() { return eleveId; }
    public void setEleveId(Long eleveId) { this.eleveId = eleveId; }
    public List<NoteDTO> getNotes() { return notes; }
    public void setNotes(List<NoteDTO> notes) { this.notes = notes; }

    // NoteDTO interne ou séparée
    public static class NoteDTO {
        @JsonIgnore
        private Long id;
        private int trimestre;
        private Double valeur;
        private Long matiereId;
        private String type;
        private Double coefficient;
        private Double note1;
        private Double note2;
        private Double classAverage;
        private Double composition;
        private Double termAverage;
        private String rank;
        private String observations;
        private String teacher;

        public NoteDTO() {}

        // Getters et setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public int getTrimestre() { return trimestre; }
        public void setTrimestre(int trimestre) { this.trimestre = trimestre; }
        public Double getValeur() { return valeur; }
        public void setValeur(Double valeur) { this.valeur = valeur; }
        public Long getMatiereId() { return matiereId; }
        public void setMatiereId(Long matiereId) { this.matiereId = matiereId; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }

        // Getters et setters pour les nouveaux champs
        public Double getCoefficient() { return coefficient; }
        public void setCoefficient(Double coefficient) { this.coefficient = coefficient; }
        public Double getNote1() { return note1; }
        public void setNote1(Double note1) { this.note1 = note1; }
        public Double getNote2() { return note2; }
        public void setNote2(Double note2) { this.note2 = note2; }
        public Double getClassAverage() { return classAverage; }
        public void setClassAverage(Double classAverage) { this.classAverage = classAverage; }
        public String getRank() { return rank; }
        public void setRank(String rank) { this.rank = rank; }
        public String getTeacher() { return teacher; }
        public void setTeacher(String teacher) { this.teacher = teacher; }
        public Double getTermAverage() { return termAverage; }
        public void setTermAverage(Double termAverage) { this.termAverage = termAverage; }
        public String getObservations() { return observations; }
        public void setObservations(String observations) { this.observations = observations; }
        public Double getComposition() { return composition; }
        public void setComposition(Double composition) { this.composition = composition; }
    }
} 
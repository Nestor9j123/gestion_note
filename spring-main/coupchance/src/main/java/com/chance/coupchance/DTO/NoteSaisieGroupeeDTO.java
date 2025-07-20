package com.chance.coupchance.DTO;

import java.util.List;

public class NoteSaisieGroupeeDTO {
    private Long idMatiere;
    private Integer trimestre;
    private List<NoteEleveDTO> notes;

    public static class NoteEleveDTO {
        private Long idEleve;
        private Double note1;
        private Double note2;
        private Double compo;
        private Integer absences;
        private Integer punitions;
        private Integer exclusions;
        private String observation;

        public Long getIdEleve() { return idEleve; }
        public void setIdEleve(Long idEleve) { this.idEleve = idEleve; }
        public Double getNote1() { return note1; }
        public void setNote1(Double note1) { this.note1 = note1; }
        public Double getNote2() { return note2; }
        public void setNote2(Double note2) { this.note2 = note2; }
        public Double getCompo() { return compo; }
        public void setCompo(Double compo) { this.compo = compo; }
        public Integer getAbsences() { return absences; }
        public void setAbsences(Integer absences) { this.absences = absences; }
        public Integer getPunitions() { return punitions; }
        public void setPunitions(Integer punitions) { this.punitions = punitions; }
        public Integer getExclusions() { return exclusions; }
        public void setExclusions(Integer exclusions) { this.exclusions = exclusions; }
        public String getObservation() { return observation; }
        public void setObservation(String observation) { this.observation = observation; }
    }

    public Long getIdMatiere() { return idMatiere; }
    public void setIdMatiere(Long idMatiere) { this.idMatiere = idMatiere; }
    public Integer getTrimestre() { return trimestre; }
    public void setTrimestre(Integer trimestre) { this.trimestre = trimestre; }
    public List<NoteEleveDTO> getNotes() { return notes; }
    public void setNotes(List<NoteEleveDTO> notes) { this.notes = notes; }
} 
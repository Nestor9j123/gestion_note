-- Ajouter la colonne bulletin_id à la table note
ALTER TABLE note ADD COLUMN bulletin_id BIGINT;
 
-- Ajouter la contrainte de clé étrangère
ALTER TABLE note ADD CONSTRAINT fk_note_bulletin FOREIGN KEY (bulletin_id) REFERENCES bulletin(id); 
package com.chance.coupchance.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Roles {
    ADMIN,
    USER;// Ajoutez d'autres rôles si nécessaire

    // Permet la désérialisation depuis une String
    @JsonCreator
    public static Roles fromString(String value) {
        if (value == null) return null;
        try {
            return Roles.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // ou lancez une exception selon votre besoin
        }
    }

    // Permet la sérialisation en String
    @JsonValue
    public String toValue() {
        return this.name();
    }
}
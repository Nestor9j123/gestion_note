package com.chance.coupchance.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRegistrationDto {

    private String name;
    private String email;
    private String password;
    @JsonProperty("isAdmin")
    private boolean admin;

    @JsonCreator
    public UserRegistrationDto(
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("isAdmin") boolean admin) { // Modifier ici aussi
        this.name = name;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }
    public UserRegistrationDto() {
    }
    

    // Getters et Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() { // Correction ici
        return admin;
    }

    public void setAdmin(boolean admin) { // Correction ici
        this.admin = admin;
    }
}

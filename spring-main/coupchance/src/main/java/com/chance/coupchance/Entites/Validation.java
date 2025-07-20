package com.chance.coupchance.Entites;

import java.time.Instant;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "validation")
public class Validation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Instant creation;
    private Instant activation;
    private Instant expiration;
    private String code;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public Instant getExpiration() {
        return expiration;
    }

    public User getUser() {
        return user;
    }
    
}

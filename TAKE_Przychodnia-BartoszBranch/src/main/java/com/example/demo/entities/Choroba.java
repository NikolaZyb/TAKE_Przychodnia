package com.example.demo;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.ArrayList;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Choroba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nazwa; 
    private String kodICD10; 

    public Choroba(String nazwa, String kodICD10) {
        this.nazwa = nazwa;
        this.kodICD10 = kodICD10;
    }
}
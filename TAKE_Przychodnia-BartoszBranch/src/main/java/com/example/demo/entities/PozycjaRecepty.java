package com.example.demo;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Getter                       
@Setter
@NoArgsConstructor 
public class PozycjaRecepty implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nazwa;
    private String dawkowanie;
    private int iloscOpakowan;

    @ManyToOne
    @JoinColumn(name = "recepta_id")
    private Recepta recepta;
    
    public PozycjaRecepty(String nazwa, String dawkowanie, int iloscOpakowan){
        this.dawkowanie = dawkowanie;
        this.nazwa = nazwa;
        this.iloscOpakowan = iloscOpakowan;
    }
}
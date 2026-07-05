package com.example.demo;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import lombok.*;

@Entity
@Getter                       
@Setter
@NoArgsConstructor
public class Doktor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private String imie;
    private String nazwisko;
    private String specjalizacja;

    @OneToMany(mappedBy = "doktor")
    private List<Wizyta> wizyty = new ArrayList<>();

    public Doktor( String imie, String nazwisko, String specjalizacja) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.specjalizacja = specjalizacja;
    }
}
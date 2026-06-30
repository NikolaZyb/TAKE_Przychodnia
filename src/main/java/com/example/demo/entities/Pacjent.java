package com.example.demo;

import com.example.demo.Wizyta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import lombok.*;

@Entity
@Getter                       
@Setter
@NoArgsConstructor
public class Pacjent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    @Column(unique = true, nullable = false) 
    private String pesel;

    private String imie;
    private String nazwisko;
    private String adres;
    private LocalDate dataUrodzenia; 

    @OneToMany(mappedBy = "pacjent")
    private List<Wizyta> wizyty= new ArrayList<>();

    public Pacjent(String pesel, String imie, String nazwisko, String adres, LocalDate dataUrodzenia) {
        this.pesel = pesel;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.adres = adres;
        this.dataUrodzenia = dataUrodzenia;
    }
}
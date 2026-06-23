package com.example.demo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Getter                       
@Setter
@NoArgsConstructor 
public class Recepta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numerRecepty;
    private LocalDate dataWystawienia; 
    private LocalDate dataWaznosci; 
    
    @ManyToOne
    @JoinColumn(name = "wizyta_id")
    private Wizyta wizyta;
    
    @OneToMany(mappedBy = "recepta", cascade = CascadeType.ALL)
    private List<PozycjaRecepty> pozycje = new ArrayList<>();
    
    public Recepta( LocalDate dataWystawienia, LocalDate dataWaznosci){
        this.dataWaznosci = dataWaznosci;
        this.dataWystawienia = dataWystawienia;
    }
}
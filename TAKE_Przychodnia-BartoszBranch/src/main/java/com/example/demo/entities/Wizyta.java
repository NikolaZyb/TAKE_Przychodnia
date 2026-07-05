package com.example.demo;

import com.example.demo.Doktor;
import com.example.demo.Pacjent;
import com.example.demo.Recepta;
import com.example.demo.StatusWizyty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter                       
@Setter
@NoArgsConstructor 
public class Wizyta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    private LocalDateTime termin;
    
    @Enumerated(EnumType.STRING)  
    private StatusWizyty status;     
    
    private String dokumentacjaMedyczna;
    
    @ManyToOne 
    @JoinColumn(name = "doktor_id") 
    private Doktor doktor;

    @ManyToOne 
    @JoinColumn(name = "pacjent_id") 
    private Pacjent pacjent;

    @OneToMany(mappedBy = "wizyta", cascade = CascadeType.ALL)
    private List<Recepta> recepty = new ArrayList<>();

    @ManyToMany 
    @JoinTable(
        name = "wizyta_choroba", 
        joinColumns = @JoinColumn(name = "wizyta_id"),
        inverseJoinColumns = @JoinColumn(name = "choroba_id")
    )
    private List<Choroba> choroby = new ArrayList<>();

    public Wizyta(LocalDateTime termin, StatusWizyty status) {
        this.termin = termin;
        this.status = status;
        this.dokumentacjaMedyczna = "";
    }
}
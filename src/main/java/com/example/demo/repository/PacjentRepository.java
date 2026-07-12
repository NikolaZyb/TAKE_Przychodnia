package com.example.demo.repository;

import com.example.demo.Pacjent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacjentRepository extends JpaRepository<Pacjent, Long> {
    
    // Zwraca pacjenta po numerze PESEL
    Optional<Pacjent> findByPesel(String pesel);
    
    // Sprawdza, czy pacjent o danym PESELu już istnieje (do scenariusza 5.2)
    boolean existsByPesel(String pesel);
}

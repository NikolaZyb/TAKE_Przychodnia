package com.example.demo.repository;

import com.example.demo.Doktor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoktorRepository extends JpaRepository<Doktor, Long> {
    
    // Zwraca listę lekarzy o podanej specjalizacji (do scenariusza 3.2)
    List<Doktor> findBySpecjalizacja(String specjalizacja);
}
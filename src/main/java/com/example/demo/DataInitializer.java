package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.demo.repository.ChorobaRepository;


@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ChorobaRepository chorobaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Sprawdzamy, czy w bazie nie ma już przypadkiem chorób
        if (chorobaRepository.count() == 0) {
            // Używamy Twojego konstruktora: public Choroba(String nazwa, String kodICD10)
            chorobaRepository.save(new Choroba("Grypa", "J11"));
            chorobaRepository.save(new Choroba("Angina", "J03"));
            chorobaRepository.save(new Choroba("Przeziębienie", "J00"));
            
            System.out.println("[DataInitializer] Pomyślnie dodano testowe choroby do bazy danych!");
        }
    }
}
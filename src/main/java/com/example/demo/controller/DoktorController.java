package com.example.demo.controller;


import com.example.demo.Doktor;
import com.example.demo.repository.DoktorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doktorzy")
public class DoktorController {

	@Autowired
    private DoktorRepository doktorRepository;

    public DoktorController(DoktorRepository doktorRepository) {
        this.doktorRepository = doktorRepository;
    }
    
    //Scenariusz 1.2. Pobranie listy wszystkich lekarzy
    //Oczekiwany wynik: 200 OK i zwrócenie listy obiektów typu Doktor.
    @GetMapping
    public ResponseEntity<List<Doktor>> getAllLekarz() {
        return ResponseEntity.ok(doktorRepository.findAll());
    }

    //Scenariusz 1.9. Dodanie nowego lekarza
    //Oczekiwany status: 201 Created.
    @PostMapping
    public ResponseEntity<Doktor> addLekarza(@RequestBody Doktor doktor) {
        Doktor zapisanyDoktor = doktorRepository.save(doktor);
        return ResponseEntity.status(HttpStatus.CREATED).body(zapisanyDoktor);
    }


    //Scenariusz 1.10. Pobranie szczegółów konkretnego lekarza
    //Oczekiwany status: 200 OK i zwrócenie danych lekarza.
    @GetMapping("/{id}")
    public ResponseEntity<Doktor> getLekarzById(@PathVariable("id") Long id) {
        return doktorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Scenariusz 1.11. Aktualizacja danych lekarza
    //Oczekiwany status: 200 OK.
    @PutMapping("/{id}")
    public ResponseEntity<Doktor> updateLekarz(@PathVariable("id") Long id, @RequestBody Doktor updateData) {
        return doktorRepository.findById(id).map(doktor -> {
            doktor.setImie(updateData.getImie());
            doktor.setNazwisko(updateData.getNazwisko());
            doktor.setSpecjalizacja(updateData.getSpecjalizacja());
            Doktor zaktualizowany = doktorRepository.save(doktor);
            return ResponseEntity.ok(zaktualizowany);
        }).orElse(ResponseEntity.notFound().build());
    }

    //Scenariusz 1.12. Usunięcie lekarza z bazy
    //Oczekiwany status: 204 No Content.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delateLekarz(@PathVariable("id") Long id) {
        if (doktorRepository.existsById(id)) {
            doktorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
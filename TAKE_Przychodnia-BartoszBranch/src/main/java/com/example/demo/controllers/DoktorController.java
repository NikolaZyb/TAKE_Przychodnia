package com.example.demo.controllers;

import com.example.demo.entities.Doktor;
import com.example.demo.repositories.DoktorRepository;
import com.example.demo.exceptions.LekarzNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doktorzy")
public class DoktorController {

    @Autowired
    private DoktorRepository doktorRepository;

    // 3.2. Pobieranie listy lekarzy po specjalizacji
    @GetMapping(params = "specjalizacja")
    public ResponseEntity<List<Doktor>> getDoktorzyBySpecjalizacja(@RequestParam String specjalizacja) {
        List<Doktor> doktorzy = doktorRepository.findBySpecjalizacja(specjalizacja);
        return ResponseEntity.ok(doktorzy);
    }

    // 5.1. Próba pobrania nieistniejącego lekarza
    @GetMapping("/{id}")
    public ResponseEntity<Doktor> getDoktorById(@PathVariable Long id) {
        Doktor doktor = doktorRepository.findById(id)
                .orElseThrow(() -> new LekarzNotFoundException("Lekarz o podanym ID nie istnieje w bazie."));
        return ResponseEntity.ok(doktor);
    }
}
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

    // 3.2. Pobieranie listy lekarzy po specjalizacji (zapytanie z parametrem)
    @GetMapping
    public ResponseEntity<List<Doktor>> getAllDoktorzy(@RequestParam(required = false) String specjalizacja) {
        if (specjalizacja != null && !specjalizacja.isEmpty()) {
            return ResponseEntity.ok(doktorRepository.findBySpecjalizacja(specjalizacja));
        }
        return ResponseEntity.ok(doktorRepository.findAll());
    }

    // 5.1. Próba pobrania nieistniejącego lekarza
    @GetMapping("/{id}")
    public ResponseEntity<Doktor> getDoktorById(@PathVariable Long id) {
        return doktorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
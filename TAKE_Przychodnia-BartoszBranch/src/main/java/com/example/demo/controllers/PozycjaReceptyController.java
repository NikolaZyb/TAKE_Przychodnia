package com.example.demo.controllers;

import com.example.demo.entities.PozycjaRecepty;
import com.example.demo.repositories.PozycjaReceptyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pozycje-recept")
public class PozycjaReceptyController {

    @Autowired
    private PozycjaReceptyRepository pozycjaReceptyRepository;

    // 2.5. Dodanie leków do recepty
    @PostMapping
    public ResponseEntity<PozycjaRecepty> addPozycjaRecepty(@RequestBody PozycjaRecepty pozycja) {
        PozycjaRecepty savedPozycja = pozycjaReceptyRepository.save(pozycja);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPozycja);
    }
}
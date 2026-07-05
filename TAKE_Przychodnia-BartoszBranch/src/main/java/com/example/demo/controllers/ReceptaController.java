package com.example.demo.controllers;

import com.example.demo.entities.Recepta;
import com.example.demo.repositories.ReceptaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recepty")
public class ReceptaController {

    @Autowired
    private ReceptaRepository receptaRepository;

    // 2.4. Wystawienie recepty
    @PostMapping
    public ResponseEntity<Recepta> createRecepta(@RequestBody Recepta recepta) {
        Recepta savedRecepta = receptaRepository.save(recepta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecepta);
    }
}
package com.example.demo.controller;

import com.example.demo.Pacjent;
import com.example.demo.repository.PacjentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacjenci")
public class PacjentController {

    @Autowired
    private PacjentRepository pacjentRepository;

    // 3.1. Wyszukiwanie pacjenta po numerze PESEL (opcjonalny parametr)
    @GetMapping
    public ResponseEntity<List<Pacjent>> getAllPacjenci(@RequestParam(required = false) String pesel) {
        if (pesel != null && !pesel.isEmpty()) {
            return pacjentRepository.findByPesel(pesel)
                    .map(pacjent -> ResponseEntity.ok(List.of(pacjent)))
                    .orElse(ResponseEntity.ok(List.of()));
        }
        return ResponseEntity.ok(pacjentRepository.findAll());
    }

    // 4.1. Pobranie wizyt pacjenta z użyciem HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pacjent>> getPacjentById(@PathVariable Long id) {
        return pacjentRepository.findById(id)
                .map(pacjent -> {
                    EntityModel<Pacjent> model = EntityModel.of(pacjent);
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PacjentController.class).getPacjentById(id)).withSelfRel());
                    
                    // Dodanie linku do wizyt w sekcji _links
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WizytaController.class).getAllWizyta()).withRel("wizyty"));
                    
                    return ResponseEntity.ok(model);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5.2. Konflikt unikalności PESEL (oraz domyślny 1.1 dla pacjenta z CRUDa)
    @PostMapping
    public ResponseEntity<?> createPacjent(@RequestBody Pacjent pacjent) {
        if (pacjent.getPesel() != null && pacjentRepository.existsByPesel(pacjent.getPesel())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Pacjent z podanym numerem PESEL już istnieje.");
        }
        Pacjent saved = pacjentRepository.save(pacjent);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}
package com.example.demo.controllers;

import com.example.demo.entities.Pacjent;
import com.example.demo.repositories.PacjentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/pacjenci")
public class PacjentController {

    @Autowired
    private PacjentRepository pacjentRepository;

    // 3.1. Wyszukiwanie pacjenta po numerze PESEL
    @GetMapping(params = "pesel")
    public ResponseEntity<Pacjent> getPacjentByPesel(@RequestParam String pesel) {
        Pacjent pacjent = pacjentRepository.findByPesel(pesel);
        if (pacjent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pacjent);
    }

    // 4.1. Pobranie wizyt pacjenta z użyciem HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pacjent>> getPacjentWithHateoas(@PathVariable Long id) {
        return pacjentRepository.findById(id).map(pacjent -> {
            EntityModel<Pacjent> entityModel = EntityModel.of(pacjent);
            
            // Dodanie linku do endpointu tworzenia wizyty (jako referencja powiązań z WizytaController)
            WebMvcLinkBuilder linkToWizyty = linkTo(methodOn(WizytaController.class).createWizyta(null)); 
            entityModel.add(linkToWizyty.withRel("wizyty"));
            
            return ResponseEntity.ok(entityModel);
        }).orElse(ResponseEntity.notFound().build());
    }
}
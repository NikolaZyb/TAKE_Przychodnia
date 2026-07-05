package com.example.demo.controllers;

import com.example.demo.entities.Wizyta;
import com.example.demo.entities.StatusWizyty;
import com.example.demo.repositories.WizytaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/wizyty")
public class WizytaController {

    @Autowired
    private WizytaRepository wizytaRepository;

    // 2.1. Utworzenie nowej wizyty
    @PostMapping
    public ResponseEntity<Wizyta> createWizyta(@RequestBody Wizyta wizyta) {
        wizyta.setStatus(StatusWizyty.ZAPLANOWANA);
        Wizyta savedWizyta = wizytaRepository.save(wizyta);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWizyta);
    }

    // 2.2. Zmiana statusu wizyty
    @PatchMapping("/{id}/status")
    public ResponseEntity<Wizyta> updateStatus(@PathVariable Long id, @RequestBody StatusWizyty nowyStatus) {
        return wizytaRepository.findById(id).map(wizyta -> {
            wizyta.setStatus(nowyStatus);
            return ResponseEntity.ok(wizytaRepository.save(wizyta));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 2.3. Dodanie dokumentacji medycznej
    @PutMapping("/{id}/dokumentacja")
    public ResponseEntity<Wizyta> updateDokumentacja(@PathVariable Long id, @RequestBody String dokumentacja) {
        return wizytaRepository.findById(id).map(wizyta -> {
            wizyta.setDokumentacjaMedyczna(dokumentacja);
            return ResponseEntity.ok(wizytaRepository.save(wizyta));
        }).orElse(ResponseEntity.notFound().build());
    }
    
    // 2.6. Zmiana terminu wizyty
    @PatchMapping("/{id}/termin")
    public ResponseEntity<Wizyta> updateTermin(@PathVariable Long id, @RequestBody LocalDateTime nowyTermin) {
        return wizytaRepository.findById(id).map(wizyta -> {
            wizyta.setTermin(nowyTermin);
            return ResponseEntity.ok(wizytaRepository.save(wizyta));
        }).orElse(ResponseEntity.notFound().build());
    }

    // 4.2. Pobranie lekarza prowadzącego wizytę (HATEOAS)
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Wizyta>> getWizytaWithHateoas(@PathVariable Long id) {
        return wizytaRepository.findById(id).map(wizyta -> {
            EntityModel<Wizyta> entityModel = EntityModel.of(wizyta);
            if (wizyta.getDoktor() != null) {
                WebMvcLinkBuilder linkToDoktor = linkTo(methodOn(DoktorController.class).getDoktorById(wizyta.getDoktor().getId())); 
                entityModel.add(linkToDoktor.withRel("lekarz_prowadzacy"));
            }
            return ResponseEntity.ok(entityModel);
        }).orElse(ResponseEntity.notFound().build());
    }
}
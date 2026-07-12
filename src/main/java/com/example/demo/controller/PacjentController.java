package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Pacjent;
import com.example.demo.repository.PacjentRepository;

@RestController
@RequestMapping("/pacjenci")
public class PacjentController {

    private final PacjentRepository pacjentRepository;

    public PacjentController(PacjentRepository pacjentRepository) {
        this.pacjentRepository = pacjentRepository;
    }


     // 1.1. Dodanie nowego pacjenta (Oczekiwany wynik: 201 Created)
     // 5.2. Konflikt unikalności PESEL (Oczekiwany wynik: 409 Conflict)
    @PostMapping
    public ResponseEntity<?> createPacjent(@RequestBody Pacjent pacjent) {
        if (pacjent.getPesel() != null && pacjentRepository.existsByPesel(pacjent.getPesel())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Pacjent z podanym numerem PESEL już istnieje.");
        }
        Pacjent saved = pacjentRepository.save(pacjent);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

     // 1.6. Pobranie listy wszystkich pacjentów
     // 3.1. Wyszukiwanie pacjenta po numerze PESEL (opcjonalny parametr)
    @GetMapping
    public ResponseEntity<List<Pacjent>> getAllPacjenci(@RequestParam(name = "pesel", required = false) String pesel) {
        if (pesel != null && !pesel.isEmpty()) {
            return pacjentRepository.findByPesel(pesel)
                    .map(pacjent -> ResponseEntity.ok(List.of(pacjent)))
                    .orElse(ResponseEntity.ok(List.of()));
        }
        return ResponseEntity.ok(pacjentRepository.findAll());
    }

    //Scenariusz 1.4. Aktualizacja danych pacjenta
    //Oczekiwany wynik: 200 OK.
    @PutMapping("/{id}")
    public ResponseEntity<Pacjent> updatePacjent(@PathVariable("id") Long id, @RequestBody Pacjent updateData) {
        return pacjentRepository.findById(id).map(pacjent -> {
            pacjent.setPesel(updateData.getPesel());
            pacjent.setImie(updateData.getImie());
            pacjent.setNazwisko(updateData.getNazwisko());
            pacjent.setAdres(updateData.getAdres());
            pacjent.setDataUrodzenia(updateData.getDataUrodzenia());
            Pacjent zaktualizowany = pacjentRepository.save(pacjent);
            return ResponseEntity.ok(zaktualizowany);
        }).orElse(ResponseEntity.notFound().build());
    }
    
    //Scenariusz 1.8. Usunięcie pacjenta z bazy
    //Oczekiwany status: 204 No Content.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePacjent(@PathVariable("id") Long id) {
        if (pacjentRepository.existsById(id)) {
            pacjentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
     // 1.7. Pobranie szczegółów konkretnego pacjenta
     // 4.1. Pobranie wizyt pacjenta z użyciem HATEOAS
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pacjent>> getPacjentById(@PathVariable("id") Long id) {
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
}

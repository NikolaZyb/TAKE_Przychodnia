package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Wizyta;
import com.example.demo.repository.WizytaRepository;

@RestController
@RequestMapping("/wizyty")
public class WizytaController {

	@Autowired
	private WizytaRepository wizytaRepository;
		
	// 1.16. Pobranie listy wszystkich wizyt: Wysłanie żądania GET na endpoint /wizyty. 
	// Oczekiwany status: 200 OK i zwrócenie listy obiektów typu Wizyta.
    @GetMapping
    public List<Wizyta> getAllWizyta() {
        return wizytaRepository.findAll();
    }

    // 1.17 & 4.2. Pobranie szczegółów konkretnej wizyty + HATEOAS (Pobranie lekarza prowadzącego)
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Wizyta>> getWizytaById(@PathVariable Long id) {
        return wizytaRepository.findById(id)
                .map(wizyta -> {
                    EntityModel<Wizyta> model = EntityModel.of(wizyta);
                    // Link do samej wizyty
                    model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WizytaController.class).getWizytaById(id)).withSelfRel());
                    
                    // 4.2. HATEOAS: Link do lekarza prowadzącego wizytę
                    if (wizyta.getDoktor() != null && wizyta.getDoktor().getId() != null) {
                        model.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DoktorController.class)
                                .getDoktorById(wizyta.getDoktor().getId())).withRel("doktor"));
                    }
                    return ResponseEntity.ok(model);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 1.18. Usunięcie wizyty z bazy: Wysłanie żądania DELETE na /wizyty/{id}. Oczekiwany
    // status: 204 No Content.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWizyta(@PathVariable Long id) {
        if (wizytaRepository.existsById(id)) {
            wizytaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // 2.1. Utworzenie nowej wizyty (Status domyślny: ZAPLANOWANA)
    @PostMapping
    public ResponseEntity<Wizyta> createWizyta(@RequestBody Wizyta wizyta) {
        // Zakładając, że masz enum StatusWizyty
        wizyta.setStatus(com.example.demo.StatusWizyty.ZAPLANOWANA);
        Wizyta saved = wizytaRepository.save(wizyta);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // 2.2, 2.3, 2.6. Modyfikacja wizyty (zmiana statusu, dodanie dokumentacji, zmiana terminu)
    @PatchMapping("/{id}")
    public ResponseEntity<Wizyta> updateWizyta(@PathVariable Long id, @RequestBody Wizyta updates) {
        return wizytaRepository.findById(id)
                .map(wizyta -> {
                    // 2.2 Zmiana statusu
                    if (updates.getStatus() != null) {
                        wizyta.setStatus(updates.getStatus());
                    }
                    // 2.3 Dodanie dokumentacji medycznej
                    if (updates.getDokumentacjaMedyczna() != null) {
                        wizyta.setDokumentacjaMedyczna(updates.getDokumentacjaMedyczna());
                    }
                    // 2.6 Zmiana terminu wizyty
                    if (updates.getTermin() != null) {
                        wizyta.setTermin(updates.getTermin());
                    }
                    
                    Wizyta saved = wizytaRepository.save(wizyta);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

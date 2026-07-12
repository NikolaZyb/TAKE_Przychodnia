package com.example.demo.controller;

import com.example.demo.Wizyta;
import com.example.demo.repository.WizytaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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
	
    // 1.17. Pobranie szczegółów konkretnej wizyty: Wysłanie żądania GET na endpoint
    // /wizyty/{id}. Oczekiwany status: 200 OK.
    @GetMapping("/{id}")
    public ResponseEntity<Wizyta> getWizytaById(@PathVariable("id") Long id) {
        return wizytaRepository.findById(id)
                .map(wizyta -> ResponseEntity.ok().body(wizyta))
                .orElse(ResponseEntity.notFound().build());
    }

    // 1.18. Usunięcie wizyty z bazy: Wysłanie żądania DELETE na /wizyty/{id}. Oczekiwany
    // status: 204 No Content.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWizyta(@PathVariable("id") Long id) {
        if (wizytaRepository.existsById(id)) {
            wizytaRepository.deleteById(id);
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.notFound().build(); 
    }
    
	
}

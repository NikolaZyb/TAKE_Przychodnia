package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.PozycjaRecepty;
import com.example.demo.repository.PozycjaReceptyRepository;

@RestController
@RequestMapping("/pozycje-recept")
public class PozycjaReceptyController {

	@Autowired
	private PozycjaReceptyRepository pozycjareceptyRepository;
	
	public PozycjaReceptyController(PozycjaReceptyRepository pozycjaReceptyRepository) {
        this.pozycjareceptyRepository = pozycjaReceptyRepository;
    }
	
	// 1.23. Pobranie listy wszystkich pozycji recept: Wysłanie żądania GET na endpoint 
	// /pozycje-recept. Oczekiwany status: 200 OK.
	@GetMapping
    public List<PozycjaRecepty> getAllRecepty() {
        return pozycjareceptyRepository.findAll();
    }
    
	// 1.24. Pobranie szczegółów konkretnej pozycji na recepcie: Wysłanie żądania GET na 
	// endpoint /pozycje-recept/{id}. Oczekiwany status: 200 OK.
    @GetMapping("/{id}")
    public ResponseEntity<PozycjaRecepty> getPozycjaById(@PathVariable("id") Long id) {
        return pozycjareceptyRepository.findById(id)
                .map(pozycja -> ResponseEntity.ok(pozycja))
                .orElse(ResponseEntity.notFound().build());
    }
    
	// 1.25. Aktualizacja pozycji na recepcie: Wysłanie żądania PUT lub PATCH na /pozycje-recept/{id} w celu modyfikacji dawkowania leku. Oczekiwany status: 200 OK.
    @PutMapping("/{id}")
    public ResponseEntity<PozycjaRecepty> updatePozycja(@PathVariable("id") Long id, @RequestBody PozycjaRecepty updatedPozycja) {
        return pozycjareceptyRepository.findById(id)
                .map(pozycja -> {
                    pozycja.setDawkowanie(updatedPozycja.getDawkowanie());
                    pozycja.setNazwa(updatedPozycja.getNazwa());
                    pozycja.setIloscOpakowan(updatedPozycja.getIloscOpakowan());
                    PozycjaRecepty saved = pozycjareceptyRepository.save(pozycja);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
	// 1.26. Usunięcie pozycji z recepty: Wysłanie żądania DELETE na /pozycje-recept/{id}. 
	// Oczekiwany status: 204 No Content.
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePozycja(@PathVariable("id") Long id) {
        pozycjareceptyRepository.deleteById(id);
    }

	// 2.5. Dodanie leków do recepty: Wysłanie żądania POST tworzącego obiekt Pozycja Recepty 
	// (nazwa leku, dawkowanie, iloscOpakowan) przypisanego do wcześniej utworzonej recepty. 
	// Oczekiwany status: 201 Created.
	@PostMapping
	public ResponseEntity<PozycjaRecepty> createPozycja(@RequestBody PozycjaRecepty pozycja) {
		PozycjaRecepty saved = pozycjareceptyRepository.save(pozycja);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
}

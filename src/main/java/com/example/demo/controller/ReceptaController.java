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
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.PozycjaRecepty;
import com.example.demo.Recepta;
import com.example.demo.repository.ReceptaRepository;

@RestController
@RequestMapping("/recepty")
public class ReceptaController {

	@Autowired
	private ReceptaRepository receptaRepository;
	
	// 1.19. Pobranie listy wszystkich recept: Wysłanie żądania GET na endpoint /recepty. 
	// Oczekiwany status: 200 OK.
	@GetMapping
    public List<Recepta> getAllRecepty() {
        return receptaRepository.findAll();
    }
	
	// 1.20. Pobranie szczegółów konkretnej recepty: Wysłanie żądania GET na endpoint 
	// /recepty/{id}. Oczekiwany status: 200 OK.
    @GetMapping("/{id}")
    public ResponseEntity<Recepta> getReceptaById(@PathVariable Long id) {
        return receptaRepository.findById(id)
                .map(recepta -> ResponseEntity.ok().body(recepta))
                .orElse(ResponseEntity.notFound().build());
    }
	
	// 1.21. Aktualizacja danych recepty: Wysłanie żądania PUT lub PATCH na /recepty/{id} w 
	// celu modyfikacji np. daty ważności. Oczekiwany status: 200 OK.
	@PutMapping("/{id}")
	public ResponseEntity<Recepta> updateRecepta(@PathVariable("id") Long id, @RequestBody Recepta updatedRecepta) {
        return receptaRepository.findById(id)
                .map(recepta -> {
                    recepta.setDataWystawienia(updatedRecepta.getDataWystawienia());
                    recepta.setDataWaznosci(updatedRecepta.getDataWaznosci());
                    if (updatedRecepta.getPozycje() != null) {
                        recepta.getPozycje().clear();
                        for (PozycjaRecepty pozycja : updatedRecepta.getPozycje()) {
                            pozycja.setRecepta(recepta); 
                            recepta.getPozycje().add(pozycja);
                        }
                    }
                    Recepta saved = receptaRepository.save(recepta);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
	// 1.22. Usunięcie recepty z bazy: Wysłanie żądania DELETE na /recepty/{id}. Oczekiwany 
	// status: 204 No Content.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecepta(@PathVariable Long id) {
        if (receptaRepository.existsById(id)) {
        	receptaRepository.deleteById(id);
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.notFound().build(); 
    }
    
	// 2.4. Wystawienie recepty: Wysłanie żądania POST dodającego nową receptę z datą
	// wystawienia i ważności oraz powiązaniem jej z identyfikatorem wizyty (wizyta_id).
	// Oczekiwany wynik: 201 Created.
	@PostMapping
	public ResponseEntity<Recepta> createRecepta(@RequestBody Recepta recepta) {
		Recepta saved = receptaRepository.save(recepta);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}
}
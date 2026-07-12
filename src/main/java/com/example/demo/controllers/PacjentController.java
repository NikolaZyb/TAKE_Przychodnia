package controller;


import com.example.demo.Pacjent;
import com.example.demo.repository.PacjentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacjenci")
public class PacjentController {
	
	@Autowired
    private PacjentRepository pacjentRepository;

    public PacjentController(PacjentRepository pacjentRepository) {
        this.pacjentRepository = pacjentRepository;
    }

    // Scenariusz 1.1. Dodanie nowego pacjenta
    //Oczekiwany wynik: 201 Created.
    @PostMapping
    public ResponseEntity<Pacjent> dodajPacjenta(@RequestBody Pacjent pacjent) {
        Pacjent zapisanyPacjent = pacjentRepository.save(pacjent);
        return ResponseEntity.status(HttpStatus.CREATED).body(zapisanyPacjent);
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
    
    //Scenariusz 1.6. Pobranie listy wszystkich pacjentów
    //Oczekiwany status: 200 OK i zwrócenie listy obiektów typu Pacjent.
    @GetMapping
    public ResponseEntity<List<Pacjent>> getAllPacjent() {
        return ResponseEntity.ok(pacjentRepository.findAll());
    }
    
    //Scenariusz 1.7. Pobranie szczegółów konkretnego pacjenta
    //Oczekiwany status: 200 OK i zwrócenie danych pacjenta.
    @GetMapping("/{id}")
    public ResponseEntity<Pacjent> getPacjentById(@PathVariable("id") Long id) {
        return pacjentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
}
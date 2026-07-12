package controller;

import com.example.demo.Choroba;
import repository.ChorobaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/choroby")
public class ChorobaController {
	@Autowired
	private ChorobaRepository chorobaRepository;
	
	public ChorobaController(ChorobaRepository chorobaRepository) {
        this.chorobaRepository = chorobaRepository;
    }
	
	//Scenariusz 1.3. Pobranie szczegółów konkretnej choroby
	//Oczekiwany wynik: 200 OK i zwrócenie danych choroby.
    @GetMapping("/{id}")
    public ResponseEntity<Choroba> getChorobyById(@PathVariable("id") Long id) {
        return chorobaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    //Scenariusz 1.5. Usunięcie choroby z bazy
    //Oczekiwany wynik: 204 No Content lub 200 OK.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChoroba(@PathVariable("id") Long id) {
        if (chorobaRepository.existsById(id)) {
            chorobaRepository.deleteById(id);
            // Zwracamy 204 No Content zgodnie z dobrą praktyką REST (oraz dokumentacją scenariusza).
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    //Scenariusz 1.13. Dodanie nowej choroby
    //Oczekiwany status: 201 Created.
    @PostMapping
    public ResponseEntity<Choroba> addChorobe(@RequestBody Choroba choroba) {
        Choroba zapisanaChoroba = chorobaRepository.save(choroba);
        return ResponseEntity.status(HttpStatus.CREATED).body(zapisanaChoroba);
    }


	// 1.14 Pobranie listy wszystkich chorób
    // Oczekiwany wynik: 200 OK i zwrócenie listy obiektów typu Choroba
	@GetMapping
    public List<Choroba> getAllChoroba() {
        return chorobaRepository.findAll();
    }
	
	// 1.15 Aktualizacja danych choroby
	// Oczekiwany status: 200 OK.
	@PutMapping("/{id}")
	public ResponseEntity<Choroba> updateChoroba(@PathVariable Long id, @RequestBody Choroba updatedChoroba) {
        return chorobaRepository.findById(id)
                .map(choroba -> {
                    choroba.setNazwa(updatedChoroba.getNazwa());
                    choroba.setKodICD10(updatedChoroba.getKodICD10());
                    Choroba saved = chorobaRepository.save(choroba);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
	

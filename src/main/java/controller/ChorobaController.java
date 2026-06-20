package controller;

import com.example.demo.Choroba;
import repository.ChorobaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/choroby")
public class ChorobaController {
	@Autowired
	private ChorobaRepository chorobaRepository;
	
	// 1.14 Pobranie listy wszystkich chorób: Wysłanie żądania GET na endpoint /choroby. 
	@GetMapping
    public List<Choroba> getAllChoroby() {
        return chorobaRepository.findAll();
    }
	
	// 1.15 Aktualizacja danych choroby: Wysłanie żądania PUT lub PATCH na /choroby/{id} 
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
	

package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pozycje-recept")
public class PozycjaReceptyController {

    // 2.5 Dodanie leków do recepty
    @PostMapping
    public ResponseEntity<PozycjaRecepty> addPozycja(@RequestBody PozycjaReceptyRequest request) {
        PozycjaRecepty savedPozycja = pozycjaReceptyService.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPozycja);
    }
}
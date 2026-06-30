package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recepty")
public class ReceptaController {

    // 2.4 Wystawienie recepty
    @PostMapping
    public ResponseEntity<Recepta> createRecepta(@RequestBody ReceptaRequest request) {
        Recepta savedRecepta = receptaService.create(request.getWizytaId(), request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRecepta);
    }
}
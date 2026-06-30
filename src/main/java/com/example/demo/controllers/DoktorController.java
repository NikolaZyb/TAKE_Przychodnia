package controller;

import com.example.demo.Doktor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/doktorzy")
public class DoktorController {

    @Autowired
    private DoktorService doktorService;

    // 3.2. Pobieranie listy lekarzy po specjalizacji: Wysłanie żądania GET na endpoint/doktorzy przekazując parametr?specjalizacja=... (np.. Kardiolog).
    @GetMapping(params = "specjalizacja")
    public ResponseEntity<List<Doktor>> getDoktorzyBySpecjalizacja(@RequestParam String specjalizacja) {
        List<Doktor> doktorzy = doktorService.findBySpecjalizacja(specjalizacja);
        return ResponseEntity.ok(doktorzy);
    }
}
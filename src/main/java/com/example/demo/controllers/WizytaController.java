package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wizyty")
public class WizytaController {

    // 2.1 Utworzenie nowej wizyty
    @PostMapping
    public ResponseEntity<Wizyta> createWizyta(@RequestBody WizytaRequest request) {
        request.setStatus(StatusWizyty.ZAPLANOWANA);
        Wizyta savedWizyta = wizytaService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedWizyta);
    }

    // 2.2 Zmiana statusu wizyty
    @PatchMapping("/{id}/status")
    public ResponseEntity<Wizyta> updateStatus(@PathVariable Long id, @RequestBody StatusWizyty nowyStatus) {
        Wizyta updated = wizytaService.updateStatus(id, nowyStatus);
        return ResponseEntity.ok(updated);
    }

    // 2.3 Dodanie dokumentacji medycznej
    @PutMapping("/{id}/dokumentacja")
    public ResponseEntity<Wizyta> addDokumentacja(@PathVariable Long id, @RequestBody String dokumentacja) {
        Wizyta updated = wizytaService.addDokumentacja(id, dokumentacja);
        return ResponseEntity.ok(updated);
    }

    // 2.6 Zmiana terminu wizyty
    @PatchMapping("/{id}/termin")
    public ResponseEntity<Wizyta> updateTermin(@PathVariable Long id, @RequestBody LocalDateTime nowyTermin) {
        Wizyta updated = wizytaService.updateTermin(id, nowyTermin);
        return ResponseEntity.ok(updated);
        
    // 4.2. Pobranie lekarza prowadzącego wizytę (HATEOAS)
        @GetMapping("/{id}")
        public ResponseEntity<EntityModel<Wizyta>> getWizytaWithHateoas(@PathVariable Long id) {
            Wizyta wizyta = wizytaService.findById(id);
            
            EntityModel<Wizyta> entityModel = EntityModel.of(wizyta);
            WebMvcLinkBuilder linkToDoktor = linkTo(methodOn(DoktorController.class).getDoktorById(wizyta.getDoktor().getId())); 
            entityModel.add(linkToDoktor.withRel("lekarz_prowadzacy"));
            
            return ResponseEntity.ok(entityModel);
    }
}
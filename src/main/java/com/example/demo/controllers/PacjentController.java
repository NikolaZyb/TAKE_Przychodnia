package controller;

import com.example.demo.Pacjent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/pacjenci")
public class PacjentController {

    @Autowired
    private PacjentService pacjentService;

    // 3.1. Wyszukiwanie pacjenta po numerze PESEL: Wysłanie żądania GET na endpoint /pacjenci przekazując parametr ?pesel...
    @GetMapping(params = "pesel")
    public ResponseEntity<Pacjent> getPacjentByPesel(@RequestParam String pesel) {
        Pacjent pacjent = pacjentService.findByPesel(pesel);
        return ResponseEntity.ok(pacjent);
    }

    // 4.1. Pobranie wizyt pacjenta z użyciem HATEOAS: Wykonanie żądania GET na/pacjenci/{id}, a następnie wykonanie kolejnego żądania GET na adres URL (link) zwrócony w odpowiedzi w sekcji_links dla powiązanych wizyt.
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Pacjent>> getPacjentWithHateoas(@PathVariable Long id) {
        Pacjent pacjent = pacjentService.findById(id);
        
        EntityModel<Pacjent> entityModel = EntityModel.of(pacjent);
        WebMvcLinkBuilder linkToWizyty = linkTo(methodOn(WizytaController.class).getWizytyByPacjentId(id)); // Zakładając taką metodę w WizytaController
        entityModel.add(linkToWizyty.withRel("wizyty"));
        
        return ResponseEntity.ok(entityModel);
    }
}
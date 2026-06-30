package controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Klasa do globalnej obsługi błędów
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 5.1. Próba pobrania nieistniejącego lekarza
    // Zastąp NotFoundException wywoływanym wyjątkiem w waszym projekcie (np. EntityNotFoundException)
    @ExceptionHandler(NotFoundException.class) 
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Zasób nie został znaleziony: " + ex.getMessage());
    }

    // 5.2. Konflikt unikalności PESEL
    @ExceptionHandler(PeselConflictException.class) // Załóżmy taki niestandardowy wyjątek z serwisu
    public ResponseEntity<String> handlePeselConflictException(PeselConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Konflikt danych: " + ex.getMessage());
    }

    // 5.3. Wysłanie błędnych typów danych (np. nieistniejący status wizyty, niezgodny z enumem)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Przekazano nieprawidłowe dane lub błędną wartość statusu. Upewnij się, że używasz dozwolonych wartości (np. ZAPLANOWANA, W_TRAKCIE).");
    }
}
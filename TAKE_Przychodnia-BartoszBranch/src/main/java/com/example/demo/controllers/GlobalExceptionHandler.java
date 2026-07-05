package com.example.demo.controllers;

import com.example.demo.exceptions.LekarzNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 5.1. Próba pobrania nieistniejącego lekarza
    @ExceptionHandler(LekarzNotFoundException.class)
    public ResponseEntity<String> handleLekarzNotFound(LekarzNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Zasób nie został znaleziony: " + ex.getMessage());
    }

    // 5.2. Konflikt unikalności PESEL
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handlePeselConflict(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Konflikt: Prawdopodobnie podany numer PESEL już istnieje w bazie danych.");
    }

    // 5.3. Wysłanie błędnych typów danych (niezgodnych z enumem)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Przekazano nieprawidłowe dane lub błędną wartość statusu. Upewnij się, że używasz dozwolonych wartości (np. ZAPLANOWANA, W_TRAKCIE).");
    }
}
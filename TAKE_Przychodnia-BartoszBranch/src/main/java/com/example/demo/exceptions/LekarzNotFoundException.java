package com.example.demo.exceptions;

public class LekarzNotFoundException extends RuntimeException {
    public LekarzNotFoundException(String message) {
        super(message);
    }
}
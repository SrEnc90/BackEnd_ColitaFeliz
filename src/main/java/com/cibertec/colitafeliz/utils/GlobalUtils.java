package com.cibertec.colitafeliz.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalUtils {

    GlobalUtils() {

    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus status) {
        String jsonMessage = "{\"message\":\"" + message + "\"}";
        return ResponseEntity.status(status).body(jsonMessage);
    }
}

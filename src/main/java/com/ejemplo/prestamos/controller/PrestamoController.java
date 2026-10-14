package com.ejemplo.prestamos.controller;

import com.ejemplo.prestamos.dto.PrestamoRequest;
import com.ejemplo.prestamos.model.Prestamo;
import com.ejemplo.prestamos.service.PrestamoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
public class PrestamoController {

    private final PrestamoService prestamoService;

    @PostMapping
    public ResponseEntity<?> registrar(@Valid @RequestBody PrestamoRequest request) {
        try {
            Prestamo creado = prestamoService.registrarPrestamo(request);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Prestamo>> consultarTodos() {
        return ResponseEntity.ok(prestamoService.listarPrestamos());
    }

    @GetMapping("/herramienta/{id}")
    public ResponseEntity<List<Prestamo>> consultarPorHerramienta(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.listarPorHerramienta(id));
    }
}
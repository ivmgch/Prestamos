package com.ejemplo.prestamos.service;

import com.ejemplo.prestamos.model.Herramienta;
import com.ejemplo.prestamos.repository.HerramientaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HerramientaService {

    private final HerramientaRepository herramientaRepository;

    public Herramienta registrarHerramienta(Herramienta herramienta) {
        if (herramienta.getCantidad() < 0) {
            throw new IllegalArgumentException("No se permiten cantidades negativas");
        }
        return herramientaRepository.save(herramienta);
    }

    public List<Herramienta> listarHerramientas() {
        return herramientaRepository.findAll();
    }
}
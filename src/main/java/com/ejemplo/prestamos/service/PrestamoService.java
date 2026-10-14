package com.ejemplo.prestamos.service;

import com.ejemplo.prestamos.dto.PrestamoRequest;
import com.ejemplo.prestamos.model.Herramienta;
import com.ejemplo.prestamos.model.Prestamo;
import com.ejemplo.prestamos.repository.HerramientaRepository;
import com.ejemplo.prestamos.repository.PrestamoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final HerramientaRepository herramientaRepository;

    @Transactional
    public Prestamo registrarPrestamo(PrestamoRequest request) {
        if (request.getCantidad() == null || request.getCantidad() <= 0) {
            throw new IllegalArgumentException("La cantidad del préstamo debe ser mayor a cero");
        }

        Herramienta herramienta = herramientaRepository.findById(request.getHerramientaId())
                .orElseThrow(() -> new IllegalArgumentException("La herramienta solicitada no existe"));

        if (request.getCantidad() > herramienta.getCantidad()) {
            throw new IllegalArgumentException("Unidades insuficientes. Disponibles: " + herramienta.getCantidad());
        }

        // Descontar unidades del inventario
        herramienta.setCantidad(herramienta.getCantidad() - request.getCantidad());
        herramientaRepository.save(herramienta);

        // Registrar préstamo en la misma transacción
        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setResponsable(request.getResponsable());
        nuevoPrestamo.setFechaPrestamo(request.getFechaPrestamo());
        nuevoPrestamo.setCantidad(request.getCantidad());
        nuevoPrestamo.setHerramienta(herramienta);

        return prestamoRepository.save(nuevoPrestamo);
    }

    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    public List<Prestamo> listarPorHerramienta(Long herramientaId) {
        return prestamoRepository.findByHerramientaId(herramientaId);
    }
}
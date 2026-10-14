package com.ejemplo.prestamos.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PrestamoRequest {

    @NotBlank(message = "El responsable es obligatorio")
    private String responsable;

    @NotBlank(message = "La fecha es obligatoria")
    private String fechaPrestamo;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad a prestar debe ser mínimo 1")
    private Integer cantidad;

    @NotNull(message = "Debe especificar el ID de la herramienta")
    private Long herramientaId;
}

package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArticuloDTO(

        @NotBlank
        String nombre,
        @NotBlank
        String descripcion,
        @NotNull
        Integer stock,
        @NotNull
        float precio
) {
}

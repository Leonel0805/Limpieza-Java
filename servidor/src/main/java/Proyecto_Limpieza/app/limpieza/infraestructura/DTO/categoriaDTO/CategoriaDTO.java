package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.categoriaDTO;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO(

        @NotBlank
        String name
) {
}

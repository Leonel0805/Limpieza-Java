package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.edificioDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EdificioDTO(

        @NotBlank
        String calle,
        @NotNull
        int numero

) {
}

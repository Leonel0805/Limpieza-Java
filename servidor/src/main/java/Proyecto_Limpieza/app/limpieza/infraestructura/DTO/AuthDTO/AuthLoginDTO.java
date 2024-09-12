package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginDTO(

        @NotBlank
        String username,

        @NotBlank
        String password
) {
}

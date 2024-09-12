package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO;

import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record AuthRegisterDTO(

        @NotBlank
    String username,
    @NotBlank
    String email,
    @NotBlank
    String password,
        @NotNull
        Set<RoleEnum> roles
) {
}

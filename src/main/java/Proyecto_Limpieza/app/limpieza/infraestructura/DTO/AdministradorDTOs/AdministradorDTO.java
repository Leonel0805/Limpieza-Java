package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs;
import Proyecto_Limpieza.app.limpieza.domain.models.roles.RoleEnum;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

public record AdministradorDTO(

        @NotBlank
        String name,

        @NotBlank(message = "Debe haber un mail")
        String email,

        @NotBlank(message = "Password no puede estar vació") //no lanza el mensaje
        String password,

        Set<RoleEnum> roles
) {
}

package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record AdministradorDTO(

        @NotBlank
        String username,

        @NotBlank(message = "Debe haber un mail")
        String email,

        @NotBlank(message = "Password no puede estar vació") //no lanza el mensaje
        String password,

        @NotNull
        Set<RoleEnum> roles

) {
}

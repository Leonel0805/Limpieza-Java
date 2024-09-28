package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs;

import jakarta.validation.constraints.NotBlank;

//tenemos que actualizar datos, la password tiene que ser en otro dto
public record PerfilAdministradorDTO (

        @NotBlank
        String username,

        @NotBlank(message = "Debe haber un mail")
        String email

){
}

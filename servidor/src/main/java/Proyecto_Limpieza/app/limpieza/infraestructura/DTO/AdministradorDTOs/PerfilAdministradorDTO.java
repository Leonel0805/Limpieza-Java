package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs;

import jakarta.validation.constraints.NotBlank;


public record PerfilAdministradorDTO (

        @NotBlank
        String name,

        @NotBlank(message = "Debe haber un mail")
        String email,

        @NotBlank(message = "Password no puede estar vació") //no lanza el mensaje
        String password

){
}

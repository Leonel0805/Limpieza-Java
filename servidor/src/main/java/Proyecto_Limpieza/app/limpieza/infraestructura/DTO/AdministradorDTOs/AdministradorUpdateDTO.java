package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import jakarta.validation.constraints.NotBlank;

//tenemos que actualizar datos, la password tiene que ser en otro dto
public record AdministradorUpdateDTO(

        @NotBlank
        String username,

        @NotBlank(message = "Debe haber un mail")
        String email

){
        public AdministradorUpdateDTO(Administrador admin) {
                this(admin.getUsername(), admin.getEmail());
        }
}

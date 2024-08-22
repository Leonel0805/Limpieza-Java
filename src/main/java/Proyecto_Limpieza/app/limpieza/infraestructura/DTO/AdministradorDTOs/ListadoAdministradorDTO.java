package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;

public record ListadoAdministradorDTO(

        Long id,
        String name,
        String email,
        String password

) {

    public ListadoAdministradorDTO(Administrador admin) {
        this(admin.getId(), admin.getUsername(), admin.getEmail(), admin.getPassword());
    }
}

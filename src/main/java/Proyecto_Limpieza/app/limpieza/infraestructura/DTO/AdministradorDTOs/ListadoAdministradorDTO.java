package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.RolesDTOs.RoleNameDTO;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record ListadoAdministradorDTO(

        Long id,
        String name,
        String email,
        String password,
        Set<RoleNameDTO> roles

) {

    public ListadoAdministradorDTO(Administrador admin) {
        this(admin.getId(), admin.getUsername(), admin.getEmail(), admin.getPassword(),
                admin.getRoles() != null ?
                        admin.getRoles().stream()
                                .map(roleEntity -> new RoleNameDTO(roleEntity))
                                .collect(Collectors.toSet()) : Collections.emptySet());

    }
}

package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.RolesDTOs;

import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEnum;

public record RoleNameDTO(

        RoleEnum rol_name
) {
    public RoleNameDTO(RoleEntity roleEntity) {
        this(roleEntity.getRoleName());
    }
}

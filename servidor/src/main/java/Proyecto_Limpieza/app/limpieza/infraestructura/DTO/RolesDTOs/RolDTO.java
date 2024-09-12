package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.RolesDTOs;

import Proyecto_Limpieza.app.limpieza.domain.models.permission.PermissionEnum;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEnum;

import java.util.Set;

public record RolDTO(

        RoleEnum roleName,
        Set<PermissionEnum> permission_names
) {
}

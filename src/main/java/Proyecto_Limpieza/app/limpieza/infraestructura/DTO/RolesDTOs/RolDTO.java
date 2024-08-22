package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.RolesDTOs;

import Proyecto_Limpieza.app.limpieza.domain.models.roles.RoleEnum;
import Proyecto_Limpieza.app.limpieza.domain.models.user.PermissionEntity;

import java.util.List;
import java.util.Set;

public record RolDTO(

        RoleEnum roleName,
        Set<Long> permission_ids
) {
}

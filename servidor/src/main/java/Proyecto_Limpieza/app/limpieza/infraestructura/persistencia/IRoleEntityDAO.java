package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEnum;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;

import java.util.Optional;

public interface IRoleEntityDAO {

    void guardarRole(RoleEntity roleEntity);

    Optional<RoleEntity> findByRoleName(RoleEnum role);
}

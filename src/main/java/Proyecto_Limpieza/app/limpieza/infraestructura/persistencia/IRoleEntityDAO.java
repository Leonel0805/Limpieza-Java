package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.roles.RoleEnum;
import Proyecto_Limpieza.app.limpieza.domain.models.user.RoleEntity;

import javax.management.relation.Role;
import java.util.Optional;

public interface IRoleEntityDAO {

    void guardarRole(RoleEntity roleEntity);

    Optional<RoleEntity> findByRoleName(RoleEnum role);
}

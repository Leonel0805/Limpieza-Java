package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.user.RoleEntity;

import javax.management.relation.Role;

public interface IRoleEntityDAO {

    void guardarRole(RoleEntity roleEntity);
}

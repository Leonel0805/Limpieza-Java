package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.user.RoleEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.RoleEntityDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleEntityService {

    @Autowired
    RoleEntityDAOImpl persistencia;

    public void guardarRoleEntity(RoleEntity roleEntity) {
        persistencia.guardarRole(roleEntity);
    }
}

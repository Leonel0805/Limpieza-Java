package Proyecto_Limpieza.app.limpieza.infraestructura.Impl;

import Proyecto_Limpieza.app.limpieza.domain.models.roles.RoleEntityRepository;
import Proyecto_Limpieza.app.limpieza.domain.models.user.RoleEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IRoleEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleEntityDAOImpl implements IRoleEntityDAO {

    @Autowired
    RoleEntityRepository roleEntityRepository;

    @Override
    public void guardarRole(RoleEntity roleEntity) {
        roleEntityRepository.save(roleEntity);
    }
}

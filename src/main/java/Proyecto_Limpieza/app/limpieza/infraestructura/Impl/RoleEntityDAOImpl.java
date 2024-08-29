package Proyecto_Limpieza.app.limpieza.infraestructura.Impl;

import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntityRepository;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEnum;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IRoleEntityDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RoleEntityDAOImpl implements IRoleEntityDAO {

    @Autowired
    RoleEntityRepository roleEntityRepository;

    @Override
    public void guardarRole(RoleEntity roleEntity) {
        roleEntityRepository.save(roleEntity);
    }

    @Override
    public Optional<RoleEntity> findByRoleName(RoleEnum role) {
        Optional<RoleEntity> rolOptional = roleEntityRepository.findByRoleName(role);
        return rolOptional;
    }
}

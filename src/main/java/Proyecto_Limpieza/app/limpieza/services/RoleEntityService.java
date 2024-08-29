package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.permission.PermissionEntityRepository;
import Proyecto_Limpieza.app.limpieza.domain.models.permission.PermissionEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.RolesDTOs.RolDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.RoleEntityDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleEntityService {

    @Autowired
    RoleEntityDAOImpl persistencia;

    @Autowired
    PermissionEntityService permissionEntityService;

    public void guardarRoleEntity(RoleEntity roleEntity) {
        persistencia.guardarRole(roleEntity);
    }

    public RoleEntity crearRolYActualizar(RolDTO rolDTO) {

        Optional<RoleEntity> roleOptional = persistencia.findByRoleName(rolDTO.roleName());

        Set<PermissionEntity> permissionList = permissionEntityService.addPermissionsRol(rolDTO);

        if (roleOptional.isEmpty()) {
            RoleEntity rolNuevo = new RoleEntity(rolDTO);
            rolNuevo.setPermissions(permissionList);

            guardarRoleEntity(rolNuevo);
            return rolNuevo;
        }

        RoleEntity rolEncontrado = roleOptional.get();
        rolEncontrado.addAllPermission(permissionList);
        guardarRoleEntity(rolEncontrado);

        return rolEncontrado;
    }


}

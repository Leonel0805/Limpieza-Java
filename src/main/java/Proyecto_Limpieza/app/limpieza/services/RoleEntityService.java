package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.roles.PermissionEntityRepository;
import Proyecto_Limpieza.app.limpieza.domain.models.user.PermissionEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.user.RoleEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.RolesDTOs.RolDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.RoleEntityDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleEntityService {

    @Autowired
    RoleEntityDAOImpl persistencia;

    @Autowired
    PermissionEntityRepository permissionEntityRepository;

    public void guardarRoleEntity(RoleEntity roleEntity) {
        persistencia.guardarRole(roleEntity);
    }

    public RoleEntity findByRoleName(RolDTO rolDTO) {

        Optional<RoleEntity> roleOptional = persistencia.findByRoleName(rolDTO.roleName());

        Set<PermissionEntity> permissionList = rolDTO.permission_ids().stream()
                .map(id -> permissionEntityRepository.findById(id))
                .filter(Optional::isPresent) // Filtra los permisos que se encontraron
                .map(Optional::get) // Obtiene el valor del Optional
                .collect(Collectors.toSet()); // Recolecta en un Set

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

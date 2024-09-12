package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.permission.PermissionEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.permission.PermissionEntityRepository;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.RolesDTOs.RolDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PermissionEntityService {

    @Autowired
    PermissionEntityRepository permissionEntityRepository;

    public Set<PermissionEntity> addPermissionsRol(RolDTO rolDTO) {
        Set<PermissionEntity> permissionList = rolDTO.permission_names().stream()
                .map(name -> permissionEntityRepository.findByName(name))
                .filter(Optional::isPresent) // Filtra los permisos que se encontraron
                .map(Optional::get) // Obtiene el valor del Optional
                .collect(Collectors.toSet()); // Recolecta en un Set

        return permissionList;
    }
}

package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;


import Proyecto_Limpieza.app.limpieza.domain.models.permission.PermissionEntityRepository;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.RolesDTOs.RolDTO;
import Proyecto_Limpieza.app.limpieza.services.RoleEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleEntityController {

    @Autowired
    RoleEntityService roleEntityService;


    @Autowired
    PermissionEntityRepository permissionEntityRepository;

    @PostMapping
    public ResponseEntity<?> guardarRoleEntity(@RequestBody RolDTO rolDTO) {

        RoleEntity rol = roleEntityService.crearRolYActualizar(rolDTO);

        return ResponseEntity.ok(rol);
    }
}

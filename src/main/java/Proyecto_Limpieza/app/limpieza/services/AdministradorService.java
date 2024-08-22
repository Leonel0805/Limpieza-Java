package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.domain.models.roles.PermissionEntityRepository;
import Proyecto_Limpieza.app.limpieza.domain.models.roles.RoleEntityRepository;
import Proyecto_Limpieza.app.limpieza.domain.models.user.RoleEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.AdministradorDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.ListadoAdministradorDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.RoleEntityDAOImpl;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IAdministradorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdministradorService {

    @Autowired
    IAdministradorDAO iAdministradorDAO;

    @Autowired
    RoleEntityRepository roleEntityRepository;

    public List<Administrador> findAll() {
        return iAdministradorDAO.findAll();
    }

    public Optional<Administrador> findById(Long id) {
        return iAdministradorDAO.findById(id);
    }

    public void guardarAdmin(Administrador admin) {
        iAdministradorDAO.guardarAdmin(admin);
    }

    public void deleteById(Long id) {
        iAdministradorDAO.deleteById(id);
    }

    public ListadoAdministradorDTO crearAdmin(AdministradorDTO administradorDTO) {

        Administrador admin = new Administrador(administradorDTO);

        Administrador adminRoles = this.asignarRoles(admin, administradorDTO);
        guardarAdmin(admin);

        return new ListadoAdministradorDTO(adminRoles);
    }

    public Administrador asignarRoles(Administrador admin, AdministradorDTO administradorDTO) {

        Set<RoleEntity> rolesList = administradorDTO.roles().stream()
                .map(roleEnum -> roleEntityRepository.findByRoleName(roleEnum))
                .filter(Optional::isPresent) // Filtra los permisos que se encontraron
                .map(Optional::get) // Obtiene el valor del Optional
                .collect(Collectors.toSet()); // Recolecta en un Set;

        System.out.println("roles asignados");
        System.out.println(rolesList);
        admin.getRoles().addAll(rolesList);


        return admin;
    }

}

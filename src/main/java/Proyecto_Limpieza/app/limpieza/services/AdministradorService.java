package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntityRepository;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEnum;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.AdministradorDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.ListadoAdministradorDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthRegisterDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.AdministradorDAOImpl;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IAdministradorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdministradorService {

    @Autowired
    AdministradorDAOImpl persistencia;

    @Autowired
    RoleEntityRepository roleEntityRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public Optional<Administrador> findById(Long id) {
        return persistencia.findById(id);
    }

    public void guardarAdmin(Administrador admin) {
        persistencia.guardarAdmin(admin);
    }

    public void deleteById(Long id) {
        persistencia.deleteById(id);
    }

    public List<Administrador> findAllIsEnabled() {

        List<Administrador> administradorList = persistencia.findAllIsEnabled();
        return administradorList;
    }

    public Administrador findByIdAndIsEnabled(Long id) {

        Optional<Administrador> adminOptional = persistencia.findByIdAndIsEnabled(id);

        if (adminOptional.isEmpty()) {
            throw new RuntimeException("No se encontró ningún Administrador");
        }

        Administrador admin = adminOptional.get();
        return admin;
    }

    public Administrador findByEmailAndIsEnabled(String email) {

        Optional<Administrador> adminOptional = persistencia.findByEmailAndIsEnabled(email);

        if (adminOptional.isEmpty()) {
            throw new RuntimeException("No se encontró ningún Administrador");
        }

        Administrador admin = adminOptional.get();
        return admin;
    }


    public Administrador crearAdmin(AdministradorDTO administradorDTO) {

        Optional<Administrador> administradorOptional = persistencia.findByEmailAndIsEnabled(administradorDTO.email());

        if (administradorOptional.isPresent()) {
            throw new RuntimeException("Email ya existente.");
        }
//        hasheamos la password
        String hashPassword = this.hashedPassword(administradorDTO.password());

        Administrador admin = new Administrador(administradorDTO, hashPassword);

        Set<RoleEntity> roles = this.obtenerRoles(administradorDTO.roles());
        admin.asignarRoles(roles);

        guardarAdmin(admin);
        return admin;
    }

//    Actualizar admin
    public Administrador actualizarAdmin(Long id, AdministradorDTO adminDTO) {

        Administrador administrador = this.findByIdAndIsEnabled(id);

        this.actualizarValores(adminDTO, administrador);
        Set<RoleEntity> roles = this.obtenerRoles(adminDTO.roles());

        roles.forEach(role -> System.out.println("Role in set: " + role.getRoleName()));
        if (roles.stream().anyMatch(roleEntity -> roleEntity.getRoleName().equals(RoleEnum.ENCARGADO))) {
            throw new RuntimeException("No se puede poner Rol Encargado a un Admin");
        }
//        denegar poder poner ENCARGADO como rol
        administrador.asignarRoles(roles);
        this.guardarAdmin(administrador);

        return administrador;
    }

    public Administrador desactivarAdmin(Long id) {

        Administrador admin = this.findByIdAndIsEnabled(id);
        if (admin == null) {
            return null;
        }

        admin.setIsEnabled(Boolean.FALSE);
        this.guardarAdmin(admin);

        return admin;
    }


    public void actualizarValores(AdministradorDTO administradorDTO, Administrador administrador) {

        administrador.setUsername(administradorDTO.name());
        administrador.setEmail(administradorDTO.email());
        administrador.setPassword(this.hashedPassword(administradorDTO.password()));
    }

    public String hashedPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public Set<RoleEntity> obtenerRoles (Set<RoleEnum> roles) {

        Set<RoleEntity> rolesList = roles.stream()
                .map(roleEnum -> roleEntityRepository.findByRoleName(roleEnum))
                .filter(Optional::isPresent) // Filtra los roles que se encontraron
                .map(Optional::get) // Obtiene el valor del Optional
                .collect(Collectors.toSet()); // Recolecta en un Set;

        return rolesList;
    }

}

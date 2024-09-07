package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEnum;
import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.PerfilDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PerfilService {

    @Autowired
    PerfilDAOImpl persistencia;

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    AdministradorService administradorService;

    @Autowired
    EncargadoService encargadoService;

    public UserEntity findByUsername(String username) {

        Optional<UserEntity> userEntityOptional =  persistencia.findByUsername(username);

        if (userEntityOptional.isEmpty()) {
            throw new RuntimeException("No se encontró ningún usuario");
        }

        return userEntityOptional.get();
    }

    public UserEntity obtenerDatos() {

        String username = userDetailService.obtenerUsuarioAutenticado();

        UserEntity user = this.findByUsername(username);

        if (user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleEnum.ADMIN))) {

//            Administrador admin = new Administrador(user.getUsername(),
//                    user.getEmail(),
//                    user.getPassword());
            Administrador admin = administradorService.findByEmailAndIsEnabled(user.getEmail());
            return admin;

        } else if (user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleEnum.ENCARGADO))) {

            Encargado encargado = encargadoService.findByUsernameAndIsEnabled(user.getUsername());
            return encargado;
        }


        return null;
    }
}

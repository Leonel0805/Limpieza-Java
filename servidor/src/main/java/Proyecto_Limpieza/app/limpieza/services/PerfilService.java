package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEnum;
import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.AdministradorUpdateDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthUpdateDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoUpdateDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.perfilDTO.PerfilUpdateDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.PerfilDAOImpl;
import Proyecto_Limpieza.app.limpieza.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    PerfilDAOImpl persistencia;

    @Autowired
    UserDetailServiceImpl userDetailService;

    @Autowired
    AdministradorService administradorService;

    @Autowired
    EncargadoService encargadoService;

    @Autowired
    JwtUtils jwtUtils;

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

            Administrador admin = administradorService.findByEmailAndIsEnabled(user.getEmail());
            return admin;

        } else if (user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleEnum.ENCARGADO))) {

            Encargado encargado = encargadoService.findByUsernameAndIsEnabled(user.getUsername());
            return encargado;
        }

        return null;
    }

    public AuthUpdateDTO actualizarPerfil(PerfilUpdateDTO perfilUpdateDTO) {

        String username = userDetailService.obtenerUsuarioAutenticado();
        UserEntity user = this.findByUsername(username);

        if (user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleEnum.ADMIN))) {

            Administrador admin = (Administrador) user;
            this.actualizarValores(perfilUpdateDTO.administrador(), admin);
            Administrador newAdmin = administradorService.guardarAdmin(admin);

            System.out.println(newAdmin.getUsername());
            UserDetails userDetails = userDetailService.loadUserByUsername(newAdmin.getUsername());

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            String jwt = jwtUtils.crearToken(authentication);

            AuthUpdateDTO response = new AuthUpdateDTO(
                    jwt,
                    newAdmin
            );

            return response;

        } else if (user.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equals(RoleEnum.ENCARGADO))) {

            Encargado encargado = (Encargado) user;
            this.actualizarValoresEncargado(perfilUpdateDTO.encargado(), encargado);
            Encargado newEncargado = encargadoService.guardarEncargado(encargado);

            UserDetails userDetails = userDetailService.loadUserByUsername(newEncargado.getUsername());

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            String jwt = jwtUtils.crearToken(authentication);

            AuthUpdateDTO response = new AuthUpdateDTO(
                    jwt,
                    newEncargado
            );

            return response;
        }
        return null;
    }


//ACTUALIZAR VALORES segun admin o
    public void actualizarValores(AdministradorUpdateDTO perfilAdministradorDTO, Administrador administrador) {

        administrador.setUsername(perfilAdministradorDTO.username());
        administrador.setEmail(perfilAdministradorDTO.email());
    }

    public void actualizarValoresEncargado(EncargadoUpdateDTO perfilEncargadoDTO, Encargado encargado) {

        encargado.setUsername(perfilEncargadoDTO.username());
        encargado.setEmail(perfilEncargadoDTO.email());
    }

    public String hashedPassword(String password) {
        return passwordEncoder.encode(password);
    }

}

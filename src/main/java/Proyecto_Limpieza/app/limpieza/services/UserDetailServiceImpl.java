package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntityRepository;
import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.user.UserRepository;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.AdministradorDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthLoginDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthRegisterDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthResponseDTO;
import Proyecto_Limpieza.app.limpieza.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdministradorService administradorService;

    @Autowired
    RoleEntityRepository roleEntityRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;


//    sobreescribir metodo loadUserByUsername, al hacer request, se busca el username
//    y se almacena en el sistema de Spring Security los authorities
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " +  username + " no existe"));


        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

//        convertimos nuestros roles y  permissions los guardamos en AuthorityList, donde spring security tiene su lógica
        userEntity.getRoles().forEach(roleEntity ->
                authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(roleEntity.getRoleName().name())))
        );

        userEntity.getRoles().stream()
                .forEach(roleEntity -> roleEntity.getPermissions().stream()
                        .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName().name())))
                );


//        retornamos un UserDetails que usa SpringSecurity
        User user = new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getIsEnabled(),
                userEntity.getAccountNoExpired(),
                userEntity.getCredentialNoExpired(),
                userEntity.getAccountNoLocked(),
                authorityList);

        return user;
    }


    //    obtener Usuario autenticado
    public String obtenerUsuarioAutenticado() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName();
        }
        return "no esta authenticado";
    }

    public AuthResponseDTO registerUser(AuthRegisterDTO authRegisterDTO) {

//        Obtenemos las credenciales enviadas
        String username = authRegisterDTO.username();
        String email = authRegisterDTO.email();
        String password = passwordEncoder.encode(authRegisterDTO.password()) ;

        if (authRegisterDTO.roles() == null) {
            throw new RuntimeException("Error, Roles no existentes");
        }

        Set<String> roles = authRegisterDTO.roles().stream()
                .map(roleEnum -> roleEnum.name()
                        .toString().toUpperCase())
                .collect(Collectors.toSet());

//        un Administrador no puede ser Encargado y un Encargado no puede tener Rol Administrador
//        Creamos usuario

        if (roles.contains("ADMIN")) {
            AdministradorDTO adminDTO = new AdministradorDTO(username, email, password, authRegisterDTO.roles());
            Administrador admin = new Administrador(adminDTO, password);

            UserEntity adminWithRoles = this.asignarRoles(admin, authRegisterDTO);
            userRepository.save(adminWithRoles);
            administradorService.guardarAdmin(admin);
            System.out.println( "asdf guardamos user y admin");
        }

//        Crear para Encargado

//        Creamos un UserDetails para spring security
        UserDetails userDetails = this.loadUserByUsername(username);
//        authenticamos de forma manual
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

//        pasar un authentication null predeterminado antes de crear token
        String accessToken = jwtUtils.crearToken(authentication);

        AuthResponseDTO response = new AuthResponseDTO(username,
                "Registrado correctamente",
                accessToken,
                true);
        return response;
    }


    public AuthResponseDTO login(AuthLoginDTO authLoginDTO) {

        String username = authLoginDTO.username();
        String password = authLoginDTO.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        System.out.println("asdf login");
        System.out.println(authentication.getName());
        String accessToken = jwtUtils.crearToken(authentication);

        AuthResponseDTO response = new AuthResponseDTO(username,
                "Logeado correctamente",
                accessToken,
                true);

        return response;
    }

    public Authentication authenticate(String username, String password) {

        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Error, username o password invalido");
        }
//        verificamos contraseña
//        si el password enviado del request, no es igual al password guardado en la database error
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Error, password incorrecto");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }


    public UserEntity asignarRoles(UserEntity user, AuthRegisterDTO authRegisterDTO) {

        Set<RoleEntity> rolesList = authRegisterDTO.roles().stream()
                .map(roleEnum -> roleEntityRepository.findByRoleName(roleEnum))
                .filter(Optional::isPresent) // Filtra los permisos que se encontraron
                .map(Optional::get) // Obtiene el valor del Optional
                .collect(Collectors.toSet()); // Recolecta en un Set;

        if (rolesList.isEmpty()) {
            throw new RuntimeException("No se encontró ni asignó ningún rol.");
        }

        user.getRoles().addAll(rolesList);
        return user;
    }

}

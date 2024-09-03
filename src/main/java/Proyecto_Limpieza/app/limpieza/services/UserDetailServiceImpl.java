package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.user.UserRepository;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthLoginDTO;
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

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;


//    sobreescribir metodo loadUserByUsername, al hacer request, se busca el username
//    y se almacena en el sistema de Spring Security los authorities
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " +  username + "no existe"));


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


    public AuthResponseDTO login(AuthLoginDTO authLoginDTO) {

        String username = authLoginDTO.username();
        String password = authLoginDTO.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

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

//        si el password enviado del request, no es igual al password guardado en la database error
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Error, password incorrecto");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());

    }

}

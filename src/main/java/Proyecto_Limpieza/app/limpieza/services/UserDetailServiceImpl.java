package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

//    sobreescribir metodo loadUserByUsername
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


        User user = new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getIsEnabled(),
                userEntity.getAccountNoExpired(),
                userEntity.getCredentialNoExpired(),
                userEntity.getAccountNoLocked(),
                authorityList);

        return user;
    }
}

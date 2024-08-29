package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

//    sobreescribir metodo loadUserByUsername
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserEntity> userEntityOptional = userRepository.findUserEntityByUsername(username);

        if (userEntityOptional.isEmpty()) {
            return null;
        }

        UserEntity user =  userEntityOptional.get();

        return null;
    }
}

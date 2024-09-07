package Proyecto_Limpieza.app.limpieza.infraestructura.Impl;

import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntityRepository;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IPerfilDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PerfilDAOImpl implements IPerfilDAO {

    @Autowired
    UserEntityRepository userEntityRepository;

    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userEntityRepository.findUserEntityByUsername(username);
    }

}

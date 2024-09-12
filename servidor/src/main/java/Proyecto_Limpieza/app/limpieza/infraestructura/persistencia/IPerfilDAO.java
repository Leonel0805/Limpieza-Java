package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;

import java.util.Optional;

public interface IPerfilDAO {

    Optional<UserEntity> findByUsername(String username);
}

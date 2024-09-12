package Proyecto_Limpieza.app.limpieza.domain.models.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);
}

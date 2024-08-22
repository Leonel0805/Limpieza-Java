package Proyecto_Limpieza.app.limpieza.domain.models.roles;

import Proyecto_Limpieza.app.limpieza.domain.models.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRoleName(RoleEnum role);
}

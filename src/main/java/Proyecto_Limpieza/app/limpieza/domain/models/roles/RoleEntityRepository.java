package Proyecto_Limpieza.app.limpieza.domain.models.roles;

import Proyecto_Limpieza.app.limpieza.domain.models.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleEntityRepository extends JpaRepository<RoleEntity, Long> {
}

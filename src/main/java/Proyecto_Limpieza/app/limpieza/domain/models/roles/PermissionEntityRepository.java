package Proyecto_Limpieza.app.limpieza.domain.models.roles;

import Proyecto_Limpieza.app.limpieza.domain.models.user.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionEntityRepository extends JpaRepository<PermissionEntity, Long> {
}

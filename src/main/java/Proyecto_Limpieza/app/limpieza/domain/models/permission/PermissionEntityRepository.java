package Proyecto_Limpieza.app.limpieza.domain.models.permission;

import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionEntityRepository extends JpaRepository<PermissionEntity, Long> {

    Optional<PermissionEntity> findByName(PermissionEnum name);
}

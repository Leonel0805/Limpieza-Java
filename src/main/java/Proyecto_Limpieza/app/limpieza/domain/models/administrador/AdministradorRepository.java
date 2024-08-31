package Proyecto_Limpieza.app.limpieza.domain.models.administrador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    @Query(value = "SELECT * FROM administradores a JOIN user_entity u ON a.id = u.id WHERE u.is_enabled = True", nativeQuery = true)
    List<Administrador> findAllIsEnabled();

    @Query(value = "SELECT * FROM administradores a JOIN user_entity u ON a.id = u.id WHERE a.id = :id AND u.is_enabled = TRUE", nativeQuery = true)
    Optional<Administrador> findByIdAndIsEnabled(@Param("id")Long id);
}

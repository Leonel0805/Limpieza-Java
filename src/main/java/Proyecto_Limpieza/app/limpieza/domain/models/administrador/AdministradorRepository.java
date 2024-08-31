package Proyecto_Limpieza.app.limpieza.domain.models.administrador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    @Query(value = "SELECT * FROM administradores a JOIN user_entity u ON a.id = u.id WHERE is_enabled = True", nativeQuery = true)
    List<Administrador> findAll();

    Optional<Administrador> findByIdAndIsEnabled(Long id);
}

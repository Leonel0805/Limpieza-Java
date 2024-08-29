package Proyecto_Limpieza.app.limpieza.domain.models.encargado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EncargadoRepository extends JpaRepository<Encargado, Long> {

    @Query(value = "SELECT * FROM encargados e JOIN user_entity u ON e.id = u.id WHERE is_enabled = True", nativeQuery = true)
    List<Encargado> findAll();

    Optional<Encargado> findByIdAndIsEnabled(Long id, Boolean isActive);
}

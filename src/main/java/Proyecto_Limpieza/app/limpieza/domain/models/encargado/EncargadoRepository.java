package Proyecto_Limpieza.app.limpieza.domain.models.encargado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EncargadoRepository extends JpaRepository<Encargado, Long> {

    @Query(value = "SELECT * FROM encargados WHERE is_active = True", nativeQuery = true)
    List<Encargado> findAll();

    Optional<Encargado> findByIdAndIsActive(Long id, Boolean isActive);
}

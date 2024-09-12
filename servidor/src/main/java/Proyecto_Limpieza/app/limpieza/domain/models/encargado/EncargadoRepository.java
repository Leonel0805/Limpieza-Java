package Proyecto_Limpieza.app.limpieza.domain.models.encargado;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EncargadoRepository extends JpaRepository<Encargado, Long> {

    @Query(value = "SELECT * FROM encargados e JOIN user_entity u ON e.id = u.id WHERE is_enabled = True", nativeQuery = true)
    List<Encargado> findAllIsEnabled();

    @Query(value = "SELECT * FROM encargados e JOIN user_entity u ON e.id = u.id WHERE e.id = :id AND u.is_enabled = TRUE", nativeQuery = true)
    Optional<Encargado> findByIdAndIsEnabled(@Param("id")Long id);

    @Query(value = "SELECT * FROM encargados e JOIN user_entity u ON e.id = u.id WHERE u.email = :email AND u.is_enabled", nativeQuery = true)
    Optional<Encargado> findByEmailAndIsEnabled(String email);

    @Query(value = "SELECT * FROM encargados e JOIN user_entity u ON e.id = u.id WHERE u.username = :username AND u.is_enabled", nativeQuery = true)
    Optional<Encargado> findByUsernameAndIsEnabled(String username);
}

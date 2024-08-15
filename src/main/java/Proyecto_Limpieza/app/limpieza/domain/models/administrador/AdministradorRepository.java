package Proyecto_Limpieza.app.limpieza.domain.models.administrador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {

    @Query(value = "SELECT * FROM administradores WHERE is_active = True", nativeQuery = true)
    List<Administrador> findAll();
}

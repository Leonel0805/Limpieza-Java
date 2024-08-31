package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;

import java.util.List;
import java.util.Optional;

public interface IAdministradorDAO {

    List<Administrador> findAllIsEnabled();
    Optional<Administrador> findById(Long id);

    Optional<Administrador> findByIdAndIsEnabled(Long id);

    void guardarAdmin(Administrador admin);

    void deleteById(Long id);

    Optional<Administrador> findByEmailAndIsEnabled(String email);
}

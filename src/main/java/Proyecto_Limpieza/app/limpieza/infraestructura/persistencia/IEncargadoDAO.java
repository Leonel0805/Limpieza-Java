package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;

import java.util.List;
import java.util.Optional;


public interface IEncargadoDAO {

    List<Encargado> findAll();
    Optional<Encargado> findById(Long id);

    void guardarEncargado(Encargado encargado);

    void deleteById(Long id);
}

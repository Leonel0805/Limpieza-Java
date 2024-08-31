package Proyecto_Limpieza.app.limpieza.infraestructura.persistencia;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.ListadoEncargadoDTO;

import java.util.List;
import java.util.Optional;


public interface IEncargadoDAO {

    List<Encargado> findAllIsEnabled();
    Optional<Encargado> findByIdAndIsEnabled(Long id);

    Optional<Encargado> findByEmailAndIsEnabled(String email);
    void guardarEncargado(Encargado encargado);


//    void deleteById(Long id);
}

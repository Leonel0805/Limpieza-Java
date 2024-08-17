package Proyecto_Limpieza.app.limpieza.infraestructura.Impl;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.EncargadoRepository;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IEncargadoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EncargadoDAOImpl implements IEncargadoDAO {

    @Autowired
    EncargadoRepository encargadoRepository;

    @Override
    public List<Encargado> findAll() {
        return encargadoRepository.findAll();
    }

    @Override
    public Optional<Encargado> findByIdAndIsActive(Long id, Boolean isActive) {
        return encargadoRepository.findByIdAndIsActive(id, isActive);
    }

    @Override
    public Encargado guardarEncargado(Encargado encargado) {
        encargadoRepository.save(encargado);
        return encargado;
    }
//
//    @Override
//    public void deleteById(Long id) {
//        encargadoRepository.deleteById(id);
//    }
}

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
    public Optional<Encargado> findById(Long id) {
        return encargadoRepository.findById(id);
    }

    @Override
    public void guardarEncargado(Encargado encargado) {
        encargadoRepository.save(encargado);
    }

    @Override
    public void deleteById(Long id) {
        encargadoRepository.deleteById(id);
    }
}

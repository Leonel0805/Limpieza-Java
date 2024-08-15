package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IEncargadoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EncargadoService implements IEncargadoDAO {

    @Autowired
    private IEncargadoDAO iEncargadoDAO;

    @Override
    public List<Encargado> findAll() {
        return iEncargadoDAO.findAll();
    }

    @Override
    public Optional<Encargado> findById(Long id) {
        return iEncargadoDAO.findById(id);
    }

    @Override
    public void guardarEncargado(Encargado encargado) {
        iEncargadoDAO.guardarEncargado(encargado);
    }

    @Override
    public void deleteById(Long id) {
        iEncargadoDAO.deleteById(id);
    }
}

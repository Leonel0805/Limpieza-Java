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
    public List<Encargado> findAllIsEnabled() {
        return encargadoRepository.findAllIsEnabled();
    }

    @Override
    public Optional<Encargado> findByIdAndIsEnabled(Long id) {
        return encargadoRepository.findByIdAndIsEnabled(id);
    }

    @Override
    public Optional<Encargado> findByEmailAndIsEnabled(String email) {
        return encargadoRepository.findByEmailAndIsEnabled(email);
    }

    @Override
    public Optional<Encargado> findByUsernameAndIsEnabled(String username) {
        return encargadoRepository.findByUsernameAndIsEnabled(username);
    }

    @Override
    public Encargado guardarEncargado(Encargado encargado) {
        Encargado encargadoResponse = encargadoRepository.save(encargado);
        return encargadoResponse;
    }
//
//    @Override
//    public void deleteById(Long id) {
//        encargadoRepository.deleteById(id);
//    }
}

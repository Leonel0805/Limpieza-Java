package Proyecto_Limpieza.app.limpieza.infraestructura.Impl;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.domain.models.administrador.AdministradorRepository;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IAdministradorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AdministradorDAOImpl implements IAdministradorDAO {

    @Autowired
    AdministradorRepository administradorRepository;

    @Override
    public List<Administrador> findAllIsEnabled() {
        return administradorRepository.findAllIsEnabled();
    }

    @Override
    public Optional<Administrador> findById(Long id) {
        return administradorRepository.findById(id);
    }

    @Override
    public Optional<Administrador> findByIdAndIsEnabled(Long id) {
        return administradorRepository.findByIdAndIsEnabled(id);
    }

    @Override
    public void guardarAdmin(Administrador admin) {
        administradorRepository.save(admin);
    }


    @Override
    public void deleteById(Long id) {
    administradorRepository.deleteById(id);
    }

    @Override
    public Optional<Administrador> findByEmailAndIsEnabled(String email) {
        return administradorRepository.findByEmailAndIsEnabled(email);
    }


}

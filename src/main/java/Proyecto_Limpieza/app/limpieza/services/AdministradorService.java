package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.AdministradorDAOImpl;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IAdministradorDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService implements IAdministradorDAO {

    @Autowired
    IAdministradorDAO iAdministradorDAO;

    @Override
    public List<Administrador> findAll() {
        return iAdministradorDAO.findAll();
    }

    @Override
    public Optional<Administrador> findById(Long id) {
        return iAdministradorDAO.findById(id);
    }

    @Override
    public void guardarAdmin(Administrador admin) {
        iAdministradorDAO.guardarAdmin(admin);
    }

    @Override
    public void deleteById(Long id) {
        iAdministradorDAO.deleteById(id);
    }
}

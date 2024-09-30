package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.edificio.Edificio;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntity;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEntityRepository;
import Proyecto_Limpieza.app.limpieza.domain.models.role.RoleEnum;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.ListadoEncargadoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.EncargadoDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EncargadoService {

    @Autowired
    private EncargadoDAOImpl persistencia;

    @Autowired
    RoleEntityRepository roleEntityRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<Encargado> findAllIsEnabled() {

        List<Encargado> encargadoList = persistencia.findAllIsEnabled();
        return encargadoList;
    }


    public Encargado findByIdAndIsEnabled(Long id) {

        Optional<Encargado> encargadoOptional = persistencia.findByIdAndIsEnabled(id);

        if (encargadoOptional.isEmpty()) {
            throw  new RuntimeException("No se encontro ningun Encargado");
        }
        Encargado encargado = encargadoOptional.get();

        return encargado;
    }


    public Encargado guardarEncargado(Encargado encargado) {
        Encargado encargadoResponse = persistencia.guardarEncargado(encargado);
        return encargadoResponse;
    }

    public Encargado findByUsernameAndIsEnabled(String username) {

        Optional<Encargado> encargadoOptional = persistencia.findByUsernameAndIsEnabled(username);

        if (encargadoOptional.isEmpty()) {
            throw new RuntimeException("No se encontró ningún encargado");
        }

        return encargadoOptional.get();
    }


    public Encargado guardarEncargado(EncargadoDTO encargadoDTO) {

//        Verificamos si hay un email existente
        Optional<Encargado> encargadoOptional = persistencia.findByEmailAndIsEnabled(encargadoDTO.email());

        if (encargadoOptional.isPresent()) {
            throw new RuntimeException("Email ya registrado.");
        }

//        hasheamos la pass al crear Encargado
        String hashPassword = this.hashedPassword(encargadoDTO.password());
        Encargado encargado = new Encargado(encargadoDTO, hashPassword);

//        seteamos rol USER a Encargado
        RoleEntity rol = roleEntityRepository.findByRoleName(RoleEnum.ENCARGADO)
                .orElseThrow(() -> new RuntimeException("Rol predeterminado no encontrado"));

        encargado.getRoles().add(rol);
        persistencia.guardarEncargado(encargado);

        return encargado;
    }

    public Encargado actualizarEncargado(Long id, EncargadoDTO encargadoDTO) {

        Encargado encargado = this.findByIdAndIsEnabled(id);

        this.actualizarValores(encargadoDTO, encargado);
        persistencia.guardarEncargado(encargado);

        return encargado;
    }


    public Encargado deleteById(Long id){

//        buscamos el encargado por id
        Encargado encargado = this.findByIdAndIsEnabled(id);

        encargado.setIsEnabled(Boolean.FALSE);
        persistencia.guardarEncargado(encargado);

        return encargado;
    }

    //    Metodos
    public void actualizarValores(EncargadoDTO encargadoDTO, Encargado encargado) {

        encargado.setUsername(encargadoDTO.username());
        encargado.setEmail(encargadoDTO.email());
        encargado.setPassword(this.hashedPassword(encargadoDTO.password()));
        encargado.setDNI(encargadoDTO.DNI());
        encargado.setApellido(encargadoDTO.apellido());
        encargado.setEdificio(
                encargadoDTO.edificio() != null ? new Edificio(encargadoDTO.edificio()) : null
        );
    }

    public String hashedPassword(String password) {
        return passwordEncoder.encode(password);
    }


}

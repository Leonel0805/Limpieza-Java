package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.edificio.Edificio;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.ListadoEncargadoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.EncargadoDAOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EncargadoService {

    @Autowired
    private EncargadoDAOImpl persistencia;


    public List<ListadoEncargadoDTO> findAll() {

        List<ListadoEncargadoDTO> encargadoList = persistencia.findAll().stream()
               .map(encargado -> new ListadoEncargadoDTO(encargado))
                .collect(Collectors.toList());

        return encargadoList;
    }


    public Optional<Encargado> findByIdAndIsEnabled(Long id, Boolean isActive) {
        return persistencia.findByIdAndIsEnabled(id, isActive);
    }


    public ListadoEncargadoDTO guardarEncargado(EncargadoDTO encargadoDTO) {

        Encargado encargado = new Encargado(encargadoDTO);
        Encargado guardado = persistencia.guardarEncargado(encargado);

        return new ListadoEncargadoDTO(guardado);
    }

    public ListadoEncargadoDTO actualizarEncargado(Long id, EncargadoDTO encargadoDTO) {

        Optional<Encargado> encargadoOptional = persistencia.findByIdAndIsEnabled(id, Boolean.TRUE);
        if (encargadoOptional.isEmpty()) {
            return null;
        }

        Encargado encargado = encargadoOptional.get();

        this.actualizarValores(encargadoDTO, encargado);
        persistencia.guardarEncargado(encargado);

        return new ListadoEncargadoDTO(encargado);
    }


    public ListadoEncargadoDTO deleteById(Long id){

//        buscamos el encargado por id
        Optional<Encargado> encargadoOptional = persistencia.findByIdAndIsEnabled(id, Boolean.TRUE);

        System.out.println(encargadoOptional);
        if (encargadoOptional.isEmpty()) {
            return null;
        }

        Encargado encargado = encargadoOptional.get();
        encargado.setIsEnabled(Boolean.FALSE);
        persistencia.guardarEncargado(encargado);

        return new ListadoEncargadoDTO(encargado);
    }

    //    Metodos
    public void actualizarValores(EncargadoDTO encargadoDTO, Encargado encargado) {

        encargado.setUsername(encargadoDTO.name());
        encargado.setEmail(encargadoDTO.email());
        encargado.setPassword(encargadoDTO.password());
        encargado.setDNI(encargadoDTO.DNI());
        encargado.setApellido(encargadoDTO.apellido());
        encargado.setEdificio(new Edificio(encargadoDTO.edificio()));
        encargado.setPedidos(encargadoDTO.pedidos());
    }

}

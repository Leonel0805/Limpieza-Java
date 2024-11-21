package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.domain.models.detallePedido.DetallePedido;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.estadoPedido.EstadoPedido;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.detallePedidoDTO.DetallePedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.estadoPedidoDTO.EstadoPedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.ListadoPedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.PedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.PedidoYDetallesDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.PedidoDAOImpl;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IPedidoDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PedidoService{

    @Autowired
    private PedidoDAOImpl persistencia;

    @Autowired
    private EncargadoService encargadoService;

    @Autowired
    private DetallePedidoService detallePedidoService;

    @Autowired
    ArticuloService articuloService;

    @Autowired
    UserDetailServiceImpl userDetailService;

    public List<ListadoPedidoDTO> findAll() {

        List<ListadoPedidoDTO> pedidosResponse = persistencia.findAll().stream()
                .map(pedido -> new ListadoPedidoDTO(pedido))
                .collect(Collectors.toList());

        return pedidosResponse;
    }

    public List<ListadoPedidoDTO> findAllNoDelete() {

        List<ListadoPedidoDTO> pedidosResponse = persistencia.findAllNoDelete().stream()
                .map(pedido -> new ListadoPedidoDTO(pedido))
                .collect(Collectors.toList());

        return pedidosResponse;
    }


    public Pedido findById(Long id) {

        Optional<Pedido> pedidoOptional = persistencia.findById(id);

        if (pedidoOptional.isEmpty()) {
            throw new RuntimeException("Pedido no encontrado");
        }

        Pedido pedido = pedidoOptional.get();

        return pedido;
    }

    public void save(Pedido pedido) {
        persistencia.guardarPedido(pedido);
    }

//    Un pedido solo puede estar relacionado con un Encargado
    public Pedido crearPedido(PedidoDTO pedidoDTO) {

        String stringEncargado;
//        Un pedido siempre al crear va a tener estado PENDIENTE

//        if (pedidoDTO.estado() == null) {
//            throw new RuntimeException("El estado del pedido no puede ser nulo o vacío.");
//        }

        if (pedidoDTO.nombre_encargado() != null) {
            stringEncargado = pedidoDTO.nombre_encargado(); //exception

        } else {
            stringEncargado = userDetailService.obtenerUsuarioAutenticado(); //lanza exception
        }

        Encargado encargado = encargadoService.findByUsernameAndIsEnabled(stringEncargado); //exception

//        creamos pedido y seteamos estado
        Pedido pedido = new Pedido(pedidoDTO, encargado);
        pedido.setEstado(EstadoPedido.PENDIENTE);

        this.save(pedido);

        return pedido;
    }

    //    creamos pedido con detalles
    public Pedido crearPedidoConDetalles(PedidoYDetallesDTO pedidoYDetallesDTO) {

        String encargadoString;

//        verificamos si enviamos un encargado por el DTO
        if (pedidoYDetallesDTO.pedido().nombre_encargado() != null) {
            encargadoString = pedidoYDetallesDTO.pedido().nombre_encargado();

        } else {
            encargadoString = userDetailService.obtenerUsuarioAutenticado();
        }

        if (encargadoString != null) {
            Encargado encargado = encargadoService.findByUsernameAndIsEnabled(encargadoString);
            Pedido pedido = new Pedido(pedidoYDetallesDTO.pedido(), encargado);
            pedidoYDetallesDTO.detalles().forEach(
                    detallePedidoDTO -> {

//                  creamos cada detalle pedido, lo guardamos y lo retornamos
                        DetallePedido detallePedido = detallePedidoService.crearDetallePedido(detallePedidoDTO);
                        pedido.addDetallePedido(detallePedido);
                    });


//        guardamos el pedido con los detalles ya almacenados
            this.save(pedido);

            return pedido;

        } else {
            throw new RuntimeException("No se pudo crear el pedido correctamente");
        }




    }

    public Pedido actualizarPedidoById(Long id, PedidoDTO pedidoDTO) {

        Pedido pedido = this.findById(id);

        if (pedidoDTO.estado() != null) {

            EstadoPedido estadoPedido = pedidoDTO.estado();
            if (estadoPedido == EstadoPedido.CANCELADO) {
//            devolvemos la cantidad de articulos a la abse de datos
                detallePedidoService.eliminarDetallesDelPedido(pedido);
            }
            pedido.setEstado(estadoPedido);

        }

        if (pedidoDTO.nombre_encargado() != null) {

            Encargado encargado = encargadoService.findByUsernameAndIsEnabled(pedidoDTO.nombre_encargado());
            pedido.setEncargado(encargado);

        }

        this.save(pedido);

        return pedido;
    }

//    PEDIDO ELIMINADO, significa descartar la operación, nunca tuvo que  entrar a PENDIENTE
    public Pedido deleteById(Long id) {

        Pedido pedido =  this.findById(id);

//        si un pedido es eliminado
        pedido.setEstado(EstadoPedido.ELIMINADO);
        detallePedidoService.eliminarDetallesDelPedido(pedido);
        this.save(pedido);

        return pedido;
    }


    public Pedido agregarDetallePedido(Long id, DetallePedidoDTO detallePedidoDTO) {

        Pedido pedido = this.findById(id);

        DetallePedido detallePedido = detallePedidoService.crearDetallePedido(detallePedidoDTO);

//        seteamos la relacion en ambas entidades
        pedido.addDetallePedido(detallePedido);
//        guardamos
        this.save(pedido);

        return pedido;
    }




}

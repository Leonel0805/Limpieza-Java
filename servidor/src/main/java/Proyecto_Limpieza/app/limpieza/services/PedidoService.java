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
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.PedidoDAOImpl;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IPedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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

        if (pedidoDTO.estado() == null) {
            throw new RuntimeException("El estado del pedido no puede ser nulo o vacío.");
        }

        if (pedidoDTO.nombre_encargado() != null) {
            stringEncargado = pedidoDTO.nombre_encargado(); //exception

        } else {
            stringEncargado = userDetailService.obtenerUsuarioAutenticado(); //lanza exception
        }

        Encargado encargado = encargadoService.findByUsernameAndIsEnabled(stringEncargado); //exception

        Pedido pedido = new Pedido(pedidoDTO, encargado);

        this.save(pedido);

        return pedido;
    }

    public Pedido actualizarPedidoById(Long id, EstadoPedidoDTO estadoPedidoDTO) {

        Pedido pedido = this.findById(id);
        EstadoPedido estadoPedido = estadoPedidoDTO.estado();

        if (estadoPedido == EstadoPedido.CANCELADO) {
//            devolvemos la cantidad de articulos a la abse de datos
            detallePedidoService.eliminarDetallesDelPedido(pedido);

        }

        pedido.setEstado(estadoPedido);
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
        pedido.setEstado(EstadoPedido.PENDIENTE);
//        guardamos
        this.save(pedido);

        return pedido;
    }




}

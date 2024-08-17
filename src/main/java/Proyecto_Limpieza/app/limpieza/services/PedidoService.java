package Proyecto_Limpieza.app.limpieza.services;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.estadoPedido.EstadoPedido;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.estadoPedidoDTO.EstadoPedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.ListadoPedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.PedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.Impl.PedidoDAOImpl;
import Proyecto_Limpieza.app.limpieza.infraestructura.persistencia.IPedidoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoService{

    @Autowired
    private PedidoDAOImpl pedidoDAOImpl;

    @Autowired
    private EncargadoService encargadoService;

    public List<ListadoPedidoDTO> findAll() {

        List<ListadoPedidoDTO> pedidosResponse = pedidoDAOImpl.findAll().stream()
                .map(pedido -> new ListadoPedidoDTO(pedido))
                .collect(Collectors.toList());

        return pedidosResponse;
    }


    public ListadoPedidoDTO findById(Long id) {

        Optional<Pedido> pedidoOptional = pedidoDAOImpl.findById(id);

        if (pedidoOptional.isEmpty()) {
            return null;
        }

        Pedido pedido = pedidoOptional.get();

        ListadoPedidoDTO pedidoDTO = new ListadoPedidoDTO(pedido);
        return  pedidoDTO;
    }

    public void save(Pedido pedido) {
        pedidoDAOImpl.guardarPedido(pedido);
    }

    public ListadoPedidoDTO guardarPedido(PedidoDTO pedidoDTO) {

        Optional<Encargado> encargadoOptional = encargadoService.findByIdIsActive(pedidoDTO.encargado_id(), Boolean.TRUE);

        if (encargadoOptional.isEmpty()) {
            return null;
        }

        Encargado encargado = encargadoOptional.get();
        Pedido pedido = new Pedido(pedidoDTO, encargado);
        this.save(pedido);

        return new ListadoPedidoDTO(pedido);
    }

    public ListadoPedidoDTO actualizarPedidoById(Long id, EstadoPedidoDTO estadoPedidoDTO) {

        Optional<Pedido> pedidoOptional = pedidoDAOImpl.findById(id);
        EstadoPedido estadoPedido = estadoPedidoDTO.estado();

        if (pedidoOptional.isEmpty()) {
            return null;
        }

        Pedido pedido = pedidoOptional.get();
        pedido.setEstado(estadoPedido);
        pedidoDAOImpl.guardarPedido(pedido);

        ListadoPedidoDTO pedidoResponse = new ListadoPedidoDTO(pedido);
        return pedidoResponse;
    }

    public ListadoPedidoDTO deleteById(Long id) {

        Optional<Pedido> pedidoOptional = pedidoDAOImpl.findById(id);

        if (pedidoOptional == null) {
            return null;
        }

        Pedido pedido = pedidoOptional.get();

        pedido.setEstado(EstadoPedido.ELIMINADO);
        this.save(pedido);


        return new ListadoPedidoDTO(pedido);
    }
}

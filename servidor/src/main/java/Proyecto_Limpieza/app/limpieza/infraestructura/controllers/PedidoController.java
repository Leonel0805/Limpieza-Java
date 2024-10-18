package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;

import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.estadoPedido.EstadoPedido;
import Proyecto_Limpieza.app.limpieza.domain.models.pedido.Pedido;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ApiResponseDTO.APIResponseDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.detallePedidoDTO.DetallePedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.estadoPedidoDTO.EstadoPedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.ListadoPedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.PedidoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.pedidoDTOs.PedidoYDetallesDTO;
import Proyecto_Limpieza.app.limpieza.services.DetallePedidoService;
import Proyecto_Limpieza.app.limpieza.services.EncargadoService;
import Proyecto_Limpieza.app.limpieza.services.PedidoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@PreAuthorize("denyAll()")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private EncargadoService encargadoService;


    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> findAll() {

        List<ListadoPedidoDTO> pedidosResponse = pedidoService.findAllNoDelete();
        return ResponseEntity.status(HttpStatus.OK).body(pedidosResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Pedido pedido = pedidoService.findById(id);
            ListadoPedidoDTO pedidoResponse = new ListadoPedidoDTO(pedido);

            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoResponse);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error - " + HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

//    POST
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO')")
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO pedidoDTO) {

        try {
            Pedido pedido = pedidoService.crearPedido(pedidoDTO);
            ListadoPedidoDTO pedidoResponse = new ListadoPedidoDTO(pedido);
            APIResponseDTO response = new APIResponseDTO(pedidoResponse, "Pedido creado correctamente!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error - BadRequest", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

//    POST crear Pedido con detalles ya incluidos
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO')")
    public ResponseEntity<?> crearPedidoDetalles(@RequestBody PedidoYDetallesDTO pedidoYDetallesDTO){

        try {
            Pedido pedido = pedidoService.crearPedidoConDetalles(pedidoYDetallesDTO);
            ListadoPedidoDTO pedidoResponse = new ListadoPedidoDTO(pedido);
            APIResponseDTO response = new APIResponseDTO(pedidoResponse, "Pedido creado correctamente!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error - BadRequest", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }


    //    POST agregar detalles al pedido
    @PostMapping("/{id}/detalle")
    @PreAuthorize("hasAnyRole('ADMIN', 'ENCARGADO')")
    public ResponseEntity<?> agregarDetallePedido(@PathVariable Long id, @RequestBody @Valid DetallePedidoDTO detallePedidoDTO) {

        try {
            Pedido pedido = pedidoService.agregarDetallePedido(id, detallePedidoDTO);
            ListadoPedidoDTO pedidoResponse = new ListadoPedidoDTO(pedido);
            APIResponseDTO response = new APIResponseDTO(pedidoResponse, "Se agrego un detalle al pedido correctamente!");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException e) {

            APIResponseDTO response = new APIResponseDTO("Error - " + HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

//    PUT
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> actualizarPedidoEstado(@PathVariable Long id, @RequestBody @Valid EstadoPedidoDTO estadoPedidoDTO) {

        try {
            Pedido pedido = pedidoService.actualizarPedidoById(id, estadoPedidoDTO);
            ListadoPedidoDTO pedidoResponse = new ListadoPedidoDTO(pedido);
            APIResponseDTO response = new APIResponseDTO(pedidoResponse, "Pedido actualizado correctamente!");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error -" + HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {

        try {
            Pedido pedido = pedidoService.deleteById(id);
            ListadoPedidoDTO pedidoResponse = new ListadoPedidoDTO(pedido);
            APIResponseDTO response = new APIResponseDTO(pedidoResponse, "Pedido 'ELIMINADO' correctamente!");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error -" + HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

}

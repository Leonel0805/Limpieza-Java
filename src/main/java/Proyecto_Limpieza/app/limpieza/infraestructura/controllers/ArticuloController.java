package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;


import Proyecto_Limpieza.app.limpieza.domain.models.articulo.Articulo;
import Proyecto_Limpieza.app.limpieza.domain.models.articulo.ArticuloRepository;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ApiResponseDTO.APIResponseDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ArticuloDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ListadoArticuloDTO;
import Proyecto_Limpieza.app.limpieza.services.ArticuloService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articulos")
@PreAuthorize("denyAll()")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

//    GET ALL
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> findAll() {

        List<ListadoArticuloDTO> list_articulosDTO = articuloService.findAll().stream()
                .map(articulo -> new ListadoArticuloDTO(articulo))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list_articulosDTO);
    }

//  GET ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CREATE') or hasAuthority('READ')")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Articulo articulo = articuloService.findById(id);

        if (articulo == null) {
            APIResponseDTO response = new APIResponseDTO("BadRequest", "No se encontró el articulo");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ListadoArticuloDTO articuloDTO = new ListadoArticuloDTO(articulo);
        return ResponseEntity.status(HttpStatus.OK).body(articuloDTO);
    }

    //    POST
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity guardarArticulo(@RequestBody @Valid ArticuloDTO articuloDTO) {

        Articulo articulo = articuloService.crearArticulo(articuloDTO);

        ListadoArticuloDTO articuloDTOList = new ListadoArticuloDTO(articulo);
        APIResponseDTO response = new APIResponseDTO(articuloDTOList, "Articulo creado correctamente");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    PUT
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity actualizarArticulo(@PathVariable Long id, @RequestBody @Valid ArticuloDTO articuloDTO) {

        Articulo articulo = articuloService.actualizarArticulo(id, articuloDTO);

        if (articulo == null) {
            APIResponseDTO response = new APIResponseDTO("Error - Bad Request", "No se encontró ningún articulo");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ListadoArticuloDTO articuloResponse = new ListadoArticuloDTO(articulo);
        APIResponseDTO response = new APIResponseDTO(articuloResponse, "Articulo actualizado correctamente!");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity eliminarArticulo(@PathVariable Long id) {

        Articulo articulo = articuloService.eliminarArticulo(id);

        if (articulo == null) {
            APIResponseDTO response = new APIResponseDTO("Error - Bad Request", "No se encontró ningún articulo");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ListadoArticuloDTO articuloResponse = new ListadoArticuloDTO(articulo);
        APIResponseDTO response = new APIResponseDTO(articuloResponse, "Articulo eliminado correctamente!");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

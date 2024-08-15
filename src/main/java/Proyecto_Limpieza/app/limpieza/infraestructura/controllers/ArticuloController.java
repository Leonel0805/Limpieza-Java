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
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/articulo")
public class ArticuloController {

    @Autowired
    private ArticuloService articuloService;

    @GetMapping
    public ResponseEntity<List<ListadoArticuloDTO>> findAll() {

        List<ListadoArticuloDTO> list_articulosDTO = articuloService.findAll().stream()
                .map(articulo -> new ListadoArticuloDTO(articulo))
                .collect(Collectors.toList());

        return ResponseEntity.ok(list_articulosDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListadoArticuloDTO> findById(@PathVariable Long id) {

        Optional<Articulo> articuloOptional = articuloService.findById(id);

        if (articuloOptional.isEmpty()) {
            APIResponseDTO response = new APIResponseDTO("BadRequest", "No se encontró el articulo");
            return ResponseEntity.notFound().build();
        }

        Articulo articulo = articuloOptional.get();
        ListadoArticuloDTO articuloDTO = new ListadoArticuloDTO(articulo);

        return ResponseEntity.ok(articuloDTO);
    }

    //    Guardar articulo
    @PostMapping
    public ResponseEntity guardarArticulo(@RequestBody @Valid ArticuloDTO articuloDTO) {

        Articulo articulo = new Articulo(articuloDTO);

        articuloService.guardarArticulo(articulo);

        ListadoArticuloDTO articuloDTOList = new ListadoArticuloDTO(articulo);

        APIResponseDTO response = new APIResponseDTO(articuloDTOList, "Articulo creado correctamente");

        return ResponseEntity.ok(response);
    }

//    Actualizar articulo

    @PutMapping("/{id}")
    public ResponseEntity actualizarArticulo(@PathVariable Long id, @RequestBody @Valid ArticuloDTO articuloDTO) {

        Optional<Articulo> articuloOptional = articuloService.findById(id);

        if (articuloOptional.isEmpty()) {
            APIResponseDTO response = new APIResponseDTO("Error - Bad Request", "No se encontró ningún articulo");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        Articulo articulo = articuloOptional.get();

        articulo.setNombre(articuloDTO.nombre());
        articulo.setDescripcion(articuloDTO.descripcion());
        articulo.setStock(articuloDTO.stock());
        articulo.setPrecio(articuloDTO.precio());

        articuloService.guardarArticulo(articulo);

        ListadoArticuloDTO articuloResponse = new ListadoArticuloDTO(articulo);
        APIResponseDTO response = new APIResponseDTO(articuloResponse, "Articulo actualizado correctamente!");

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity eliminarArticulo(@PathVariable Long id) {

        Optional<Articulo> articuloOptional = articuloService.findById(id);

        if (articuloOptional.isEmpty()) {
            APIResponseDTO apiResponseDTO = new APIResponseDTO("Error - Bad Request", "No se encontró ningún articulo");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponseDTO);
        }

        Articulo articulo = articuloOptional.get();
        articulo.setStock(0);
        articulo.setSin_stock(Boolean.TRUE);

        articuloService.guardarArticulo(articulo);

        ListadoArticuloDTO articuloResponse = new ListadoArticuloDTO(articulo);
        APIResponseDTO response = new APIResponseDTO(articuloResponse, "Articulo eliminado correctamente!");

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }
}

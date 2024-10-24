package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;

import Proyecto_Limpieza.app.limpieza.domain.models.categoria.Categoria;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ApiResponseDTO.APIResponseDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ArticuloDTO.ListadoArticuloDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.categoriaDTO.CategoriaDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.categoriaDTO.ListadoCategoriaDTO;
import Proyecto_Limpieza.app.limpieza.services.CategoriaService;
import com.cloudinary.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categorias")
@PreAuthorize("denyAll()")
public class CategoriaController {

    @Autowired
    CategoriaService categoriaService;

//    Find All
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> findAll() {

        List<ListadoCategoriaDTO> categoriaDTOList = categoriaService.findAll().stream()
                .map(categoria -> new ListadoCategoriaDTO(categoria))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(categoriaDTOList);
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {

        try {
            Categoria categoria = categoriaService.crearCategoria(categoriaDTO);
            ListadoCategoriaDTO categoriaResponse = new ListadoCategoriaDTO(categoria);
            APIResponseDTO response = new APIResponseDTO(categoriaResponse, "Categoría creada con éxito");

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {

            APIResponseDTO response = new APIResponseDTO("Error: "+ HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}

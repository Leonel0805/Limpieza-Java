package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;

import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ApiResponseDTO.APIResponseDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.AdministradorDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.ListadoAdministradorDTO;
import Proyecto_Limpieza.app.limpieza.services.AdministradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/administradores")
@PreAuthorize("denyAll()")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

//    Get All
    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> findAll() {
//        Convertimos a lista de DTO para mostrar los datos que queremos
        List<ListadoAdministradorDTO> list_administradores = administradorService.findAllIsEnabled().stream()
                .map(administrador -> new ListadoAdministradorDTO(administrador))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list_administradores);
    }

//    Get
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        try {
            Administrador admin = administradorService.findByIdAndIsEnabled(id);
            ListadoAdministradorDTO adminResponse = new ListadoAdministradorDTO(admin);
            return ResponseEntity.status(HttpStatus.OK).body(adminResponse);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error -" + HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

//    POST falta verificar si existe el email
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity guardarAdmin(@RequestBody @Valid AdministradorDTO administradorDTO){

        try {
            Administrador admin = administradorService.crearAdmin(administradorDTO);
            ListadoAdministradorDTO adminResponse = new ListadoAdministradorDTO(admin);
            APIResponseDTO response = new APIResponseDTO(adminResponse, "Administrador creado correctamente!");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error - " + HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

//    PUT
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity actualizarAdmin(@PathVariable Long id, @RequestBody @Valid AdministradorDTO administradorDTO) {

        try {
            Administrador admin = administradorService.actualizarAdmin(id, administradorDTO);
            ListadoAdministradorDTO adminResponse = new ListadoAdministradorDTO(admin);
            APIResponseDTO response = new APIResponseDTO(adminResponse, "Administrador actualizado correctamente!");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error - " + HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }

//    DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity deleteById(@PathVariable Long id) {

        try {
            Administrador admin = administradorService.desactivarAdmin(id);
            ListadoAdministradorDTO adminDTO = new ListadoAdministradorDTO(admin);
            APIResponseDTO response = new APIResponseDTO(adminDTO, "Aministrador 'eliminado'");

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error - " + HttpStatus.NOT_FOUND, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

    }
}

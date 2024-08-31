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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

//    Get All
    @GetMapping
    public ResponseEntity<?> findAll() {
//        Convertimos a lista de DTO para mostrar los datos que queremos
        List<ListadoAdministradorDTO> list_administradores = administradorService.findAllIsEnabled().stream()
                .map(administrador -> new ListadoAdministradorDTO(administrador))
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(list_administradores);
    }

//    Get
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {

        Administrador admin = administradorService.findByIdAndIsEnabled(id);

        if (admin == null) {
            APIResponseDTO response = new APIResponseDTO("Error - BadRequest", "No se pudo encontrar ningún administrador");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ListadoAdministradorDTO adminResponse = new ListadoAdministradorDTO(admin);
        return ResponseEntity.status(HttpStatus.OK).body(adminResponse);
    }

//    POST
    @PostMapping
    public ResponseEntity guardarAdmin(@RequestBody @Valid AdministradorDTO administradorDTO){

        Administrador admin = administradorService.crearAdmin(administradorDTO);

        if (admin == null) {
            APIResponseDTO response = new APIResponseDTO("Error - BadRequest", "No se pudo crear el admin.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ListadoAdministradorDTO adminResponse = new ListadoAdministradorDTO(admin);
        APIResponseDTO response = new APIResponseDTO(adminResponse, "Administrador creado correctamente!");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

//    PUT
    @PutMapping("/{id}")
    public ResponseEntity actualizarAdmin(@PathVariable Long id, @RequestBody @Valid AdministradorDTO administradorDTO) {

//        Obtenemos el admin por id
        Administrador admin = administradorService.actualizarAdmin(id, administradorDTO);

        if (admin == null) {
            APIResponseDTO response = new APIResponseDTO("Error - BadRequest", "No se pudo actualizar el admin.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ListadoAdministradorDTO adminResponse = new ListadoAdministradorDTO(admin);
        APIResponseDTO response = new APIResponseDTO(adminResponse, "Administrador actualizado correctamente!");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {

        Administrador admin = administradorService.desactivarAdmin(id);

        if (admin == null) {
            APIResponseDTO response = new APIResponseDTO("Error - BadRequest", "No se encontro ningún administrador");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(admin);
        }

        ListadoAdministradorDTO adminDTO = new ListadoAdministradorDTO(admin);
        APIResponseDTO response = new APIResponseDTO(adminDTO, "Aministrador 'eliminado'");

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

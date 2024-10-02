package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;


import Proyecto_Limpieza.app.limpieza.domain.models.administrador.Administrador;
import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;
import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.AdministradorUpdateDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AdministradorDTOs.ListadoAdministradorDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthUpdateDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthUpdateResponseDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.EncargadoUpdateDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO.ListadoEncargadoDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.perfilDTO.PerfilUpdateDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.userDTO.ResponseUpdateUserGenericDTO;
import Proyecto_Limpieza.app.limpieza.services.PerfilService;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("api/me")
public class PerfilController {

    @Autowired
    PerfilService perfilService;

//    GET
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> mePerfil() {

        UserEntity user = perfilService.obtenerDatos();

        if (user != null) {
            if (user instanceof Administrador) {

                Administrador admin = (Administrador) user;
                ListadoAdministradorDTO response = new ListadoAdministradorDTO(admin);
                return ResponseEntity.status(HttpStatus.OK).body(response);


            } else if (user instanceof Encargado) {

                Encargado encargado = (Encargado) user;
                ListadoEncargadoDTO response = new ListadoEncargadoDTO(encargado);
                return ResponseEntity.status(HttpStatus.OK).body(response);

            }

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
    

    //    PATCH
    @PatchMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> actualizarPerfil(@RequestBody PerfilUpdateDTO perfilUpdateDTO) {

        AuthUpdateDTO responseDTO = perfilService.actualizarPerfil(perfilUpdateDTO);

        if (responseDTO.user() != null) {
            if (responseDTO.user() instanceof Administrador) {

                Administrador admin = (Administrador) responseDTO.user();
                ResponseUpdateUserGenericDTO adminResponse = new ResponseUpdateUserGenericDTO(admin);
                AuthUpdateResponseDTO response = new AuthUpdateResponseDTO(adminResponse, responseDTO.jwt(), "Usuario actualizado correctamente");
                return ResponseEntity.status(HttpStatus.OK).body(response);

            } else if (responseDTO.user() instanceof Encargado) {

                Encargado encargado = (Encargado) responseDTO.user();
                ResponseUpdateUserGenericDTO encargadoResponse = new ResponseUpdateUserGenericDTO(encargado);

                AuthUpdateResponseDTO response = new AuthUpdateResponseDTO(encargadoResponse, responseDTO.jwt(), "Usuario actualizado correctamente");
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }
}

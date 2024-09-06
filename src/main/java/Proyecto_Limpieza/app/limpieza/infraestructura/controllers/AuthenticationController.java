package Proyecto_Limpieza.app.limpieza.infraestructura.controllers;


import Proyecto_Limpieza.app.limpieza.domain.models.user.UserEntity;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.ApiResponseDTO.APIResponseDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthLoginDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthRegisterDTO;
import Proyecto_Limpieza.app.limpieza.infraestructura.DTO.AuthDTO.AuthResponseDTO;
import Proyecto_Limpieza.app.limpieza.services.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    UserDetailServiceImpl userDetailService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid AuthRegisterDTO authRegisterDTO) {

        try {
            AuthResponseDTO userResponse = this.userDetailService.registerUser(authRegisterDTO);

            return ResponseEntity.status(HttpStatus.OK).body(userResponse);

        } catch (RuntimeException e) {
            APIResponseDTO response = new APIResponseDTO("Error -" + HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser (@RequestBody @Valid AuthLoginDTO authLoginDTO) {

        AuthResponseDTO response  = this.userDetailService.login(authLoginDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

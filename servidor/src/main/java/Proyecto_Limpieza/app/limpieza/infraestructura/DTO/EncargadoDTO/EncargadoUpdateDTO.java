package Proyecto_Limpieza.app.limpieza.infraestructura.DTO.EncargadoDTO;


import Proyecto_Limpieza.app.limpieza.domain.models.encargado.Encargado;

public record EncargadoUpdateDTO(

        String username,
        String email,

        String DNI,
        String apellido

//        EdificioDTO edificio
) {
    public EncargadoUpdateDTO(Encargado encargado) {
        this(encargado.getUsername(), encargado.getEmail(), encargado.getDNI(), encargado.getApellido());
    }
}

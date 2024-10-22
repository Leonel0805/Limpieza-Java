package Proyecto_Limpieza.app.limpieza.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    Cloudinary cloudinary;

    public String cargarImagen(MultipartFile file) throws IOException {

        Map<String, Object> params = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true
        );

        // Realiza la subida del archivo
        Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

        // Imprime el resultado
        System.out.println("Este es el resultado de la subida: " + uploadResult);

        // Retorna la URL de la imagen
        return (String) uploadResult.get("url");
    }

}

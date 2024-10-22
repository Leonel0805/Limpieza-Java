package Proyecto_Limpieza.app.limpieza;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.Map;

@SpringBootApplication
public class Application {

	@Value("${CLOUDINARY_URL}")
	private String cloudinaryUrl;

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

	@PostConstruct
	public void init() {
		System.out.println("Cloudinary URL: " + cloudinaryUrl); // Imprime la URL de Cloudinary
	}

}

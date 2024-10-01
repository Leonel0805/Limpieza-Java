package Proyecto_Limpieza.app.limpieza.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permite CORS en todos los endpoints
                .allowedOrigins("*") // Orígenes permitidos
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE") // Métodos permitidos
                .allowedHeaders("Authorization", "Content-Type"); // Encabezados permitidos
    }
}
package pe.celucheck.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI celuCheckOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("CeluCheck API")
                        .version("1.0.0")
                        .description("API REST del comparador CeluCheck: catálogo, recomendaciones y favoritos.")
                        .contact(new Contact().name("Equipo CeluCheck"))
                        .license(new License().name("Uso académico / demostrativo")))
                .servers(List.of(
                        new Server().url("https://celucheck.onrender.com").description("Producción - Render"),
                        new Server().url("http://localhost:8080").description("Desarrollo local")
                ))
                .components(new Components().addSecuritySchemes("sessionCookie",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.COOKIE)
                                .name("JSESSIONID")
                                .description("Sesión creada mediante el formulario /login.")));
    }
}

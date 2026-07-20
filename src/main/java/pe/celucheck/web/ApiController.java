package pe.celucheck.web;

import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pe.celucheck.dto.*;
import pe.celucheck.service.*;
import java.math.BigDecimal;
import java.util.*;

@RestController @RequestMapping("/api") @RequiredArgsConstructor
@Tag(name = "CeluCheck", description = "Operaciones públicas del catálogo, asistente y favoritos del usuario")
public class ApiController {
    private final CatalogService catalog; private final FavoriteService favorites; private final ChatAssistantService chat;
    @GetMapping("/products")
    @Operation(summary = "Buscar celulares", description = "Filtra el catálogo activo por texto, marca, precio máximo o perfil de uso.")
    @ApiResponse(responseCode = "200", description = "Listado de productos encontrados")
    List<ProductCard> products(
            @Parameter(description = "Marca, modelo o procesador") @RequestParam(defaultValue="") String q,
            @Parameter(description = "Slug de la marca, por ejemplo samsung") @RequestParam(defaultValue="") String brand,
            @Parameter(description = "Precio máximo en soles") @RequestParam(required=false) BigDecimal maxPrice,
            @Parameter(description = "Perfil: camera, gaming, battery, value o compact") @RequestParam(defaultValue="") String profile){
        return catalog.search(q,brand,maxPrice,profile);
    }

    @PostMapping("/favorites/{phoneId}")
    @Operation(summary = "Guardar o quitar un favorito", description = "Alterna el celular indicado en los favoritos del usuario autenticado.", security = @SecurityRequirement(name = "sessionCookie"))
    @Parameter(name = "JSESSIONID", in = ParameterIn.COOKIE, description = "Cookie de sesión autenticada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "401", description = "Se requiere iniciar sesión", content = @Content)
    })
    Map<String,Object> toggle(@Parameter(description = "Identificador del celular") @PathVariable Long phoneId, Authentication auth){
        boolean saved=favorites.toggle(auth.getName(),phoneId); return Map.of("saved",saved);
    }

    @PostMapping("/chat")
    @Operation(summary = "Consultar al asistente", description = "Genera una recomendación basada en reglas de presupuesto, cámara, batería y plataforma; guarda el historial de la consulta.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Recomendación generada", content = @Content(schema = @Schema(implementation = ChatResponse.class))),
            @ApiResponse(responseCode = "400", description = "Mensaje vacío o mayor de 1000 caracteres", content = @Content)
    })
    ChatResponse chat(@Valid @RequestBody ChatRequest request, Authentication auth){
        return new ChatResponse(chat.answer(request.message(),auth==null?null:auth.getName()));
    }
}

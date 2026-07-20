package pe.celucheck.dto;
import io.swagger.v3.oas.annotations.media.Schema;
@Schema(description = "Respuesta generada por el asistente CeluCheck")
public record ChatResponse(
        @Schema(description = "Recomendación explicada en lenguaje sencillo") String answer) {}

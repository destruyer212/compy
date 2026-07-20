package pe.celucheck.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
@Schema(description = "Consulta para el asistente de recomendaciones")
public record ChatRequest(
        @Schema(description = "Pregunta o necesidad del comprador", example = "Busco un celular con buena batería por menos de S/ 2000")
        @NotBlank @Size(max=1000) String message) {}

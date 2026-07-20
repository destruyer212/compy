package pe.celucheck.dto;
import jakarta.validation.constraints.*;
public record ChatRequest(@NotBlank @Size(max=1000) String message) {}

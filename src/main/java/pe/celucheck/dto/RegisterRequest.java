package pe.celucheck.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {
    @NotBlank @Size(min=3,max=120) private String fullName;
    @NotBlank @Email @Size(max=180) private String email;
    @NotBlank @Size(min=8,max=72) private String password;
    @NotBlank private String confirmPassword;
}

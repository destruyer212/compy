package pe.celucheck.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter @Setter
public class AdminPhoneForm {
    private Long id;
    @NotNull private Long brandId;
    @NotBlank private String name;
    @NotBlank private String model;
    @NotNull @Min(16) private Integer storageGb;
    @NotNull @Min(1) private Integer ramGb;
    @NotNull @DecimalMin("3.0") private BigDecimal screenInches;
    @NotNull @Min(1) private Integer cameraMp;
    @NotNull @Min(1000) private Integer batteryMah;
    @NotBlank private String processor;
    @NotBlank private String imageUrl;
    @NotBlank private String description;
    private boolean featured;
    private BigDecimal falabellaPrice;
    private BigDecimal ripleyPrice;
    private BigDecimal oechslePrice;
}

package nuam.productsapi.dto;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDto {
    private Long id;
    @NotBlank(message = "The product name cannot be blank.")
    @Size(min = 3, message = "The product name must have at least 3 characters.")
    private String name;
    private String description;
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    private BigDecimal price;
    private LocalDateTime createdAt;
}

package nuam.products.api.dto.response;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0.")
    private BigDecimal price;
    private LocalDateTime createdAt;
}

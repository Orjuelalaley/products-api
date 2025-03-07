package nuam.products.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse implements Serializable {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private Map<String, String> details;
}
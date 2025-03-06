package nuam.products.api.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super(String.valueOf(id));
    }
}

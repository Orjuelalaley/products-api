package nuam.productsapi.controller;

import jakarta.validation.Valid;
import nuam.productsapi.dto.ProductDto;
import nuam.productsapi.service.intf.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final IProductService productService;

    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    /**
     * POST /products
     * Crea un nuevo producto.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDto createProduct(@Valid @RequestBody ProductDto productDto) {
        // Delegamos la creación a la capa de servicio.
        return productService.createProduct(productDto);
    }

    /**
     * GET /products
     * Obtiene todos los productos.
     */
    @GetMapping
    public List<ProductDto> getAllProducts() {
        // El controlador no contiene lógica de negocio, solo delega en el servicio.
        return productService.getAllProducts();
    }

    /**
     * GET /products/{id}
     * Obtiene un producto por ID.
     */
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    /**
     * PUT /products/{id}
     * Actualiza un producto existente.
     */
    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id,
                                    @Valid @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }

    /**
     * DELETE /products/{id}
     * Elimina un producto por ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
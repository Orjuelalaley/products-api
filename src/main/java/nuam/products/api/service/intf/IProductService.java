package nuam.products.api.service.intf;


import nuam.products.api.dto.request.ProductDto;
import nuam.products.api.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    ProductResponse createProduct(ProductDto productRequest);

    Page<ProductResponse> getAllProducts(Pageable pageable);

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductDto productRequest);

    void deleteProduct(Long id);
}
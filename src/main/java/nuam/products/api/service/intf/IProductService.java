package nuam.products.api.service.intf;


import nuam.products.api.dto.request.ProductDto;
import nuam.products.api.dto.response.ProductResponse;

import java.util.List;

public interface IProductService {
    ProductResponse createProduct(ProductDto productRequest);

    List<ProductResponse> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductDto productRequest);

    void deleteProduct(Long id);
}
package nuam.productsapi.service.intf;

import nuam.productsapi.dto.request.ProductRequest;
import nuam.productsapi.dto.response.ProductResponse;
import nuam.productsapi.entity.Product;

import java.util.List;

public interface IProductService {
    ProductResponse createProduct(ProductRequest productRequest);

    List<Product> getAllProducts();

    ProductResponse getProductById(Long id);

    ProductResponse updateProduct(Long id, ProductRequest productRequest);

    void deleteProduct(Long id);
}

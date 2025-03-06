package nuam.productsapi.service.intf;


import nuam.productsapi.dto.request.ProductDto;

import java.util.List;

public interface IProductService {
    ProductDto createProduct(ProductDto productRequest);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    ProductDto updateProduct(Long id, ProductDto productRequest);

    void deleteProduct(Long id);
}
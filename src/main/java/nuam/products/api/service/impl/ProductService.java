package nuam.products.api.service.impl;

import nuam.products.api.dto.request.ProductDto;
import nuam.products.api.entity.Product;
import nuam.products.api.repository.ProductRepository;
import nuam.products.api.service.intf.IProductService;
import nuam.products.api.dto.response.ProductResponse;
import nuam.products.api.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return convertToDto(product);
    }

    @Override
    public ProductResponse createProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto);
        Product savedProduct = productRepository.save(product);
        return convertToDto(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductDto productDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        updateEntityFromDto(product, productDto);
        Product updatedProduct = productRepository.save(product);
        return convertToDto(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        productRepository.delete(product);
    }

    private ProductResponse convertToDto(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .createdAt(product.getCreatedAt())
                .build();
    }

    private Product convertToEntity(ProductDto productDto) {
        return Product.builder()
                .name(productDto.getName())
                .description(productDto.getDescription())
                .price(productDto.getPrice())
                .createdAt(productDto.getCreatedAt())
                .build();
    }

    private void updateEntityFromDto(Product product, ProductDto productDto) {
        // Aquí se actualizan únicamente los atributos modificables
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
    }
}

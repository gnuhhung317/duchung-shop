package net.duchung.shop_app.service;

import net.duchung.shop_app.dto.ProductDto;
import net.duchung.shop_app.dto.ProductImageDto;
import net.duchung.shop_app.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    // Define your service methods here
    ProductDto createProduct(ProductDto productDTO) ;
    ProductDto getProductById(long id) throws Exception;
    Page<ProductDto> getAllProducts(String keyword,Long categoryId,PageRequest pageRequest);
    ProductDto updateProduct(long id, ProductDto productDTO) ;
    void deleteProduct(long id);
    boolean existsByName(String name);
    ProductImageDto createProductImage(
            Long productId,
            ProductImageDto productImageDTO) ;
}

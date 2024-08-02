package net.duchung.shop_app.service.impl;

import net.duchung.shop_app.dto.ProductDto;
import net.duchung.shop_app.dto.ProductImageDto;
import net.duchung.shop_app.entity.Category;
import net.duchung.shop_app.entity.Product;
import net.duchung.shop_app.entity.ProductImage;
import net.duchung.shop_app.exception.DataNotFoundException;
import net.duchung.shop_app.repository.CategoryRepository;
import net.duchung.shop_app.repository.ProductImageRepository;
import net.duchung.shop_app.repository.ProductRepository;
import net.duchung.shop_app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private  ProductImageRepository productImageRepository;
    @Override
    public ProductDto createProduct(ProductDto productDTO)  {
        Category category=categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new DataNotFoundException("Category not found"));
        Product product = toEntity(productDTO);
        product.setCategory(category);
        productRepository.save(product);
        return toDto(product);
    }

    @Override
    public ProductDto getProductById(long id) throws Exception {
        return toDto(productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found")));
    }

    @Override
    public Page<ProductDto> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(this::toDto);
    }

    @Override
    public ProductDto updateProduct(long id, ProductDto productDTO)  {
        Product product = productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Product not found"));
        Category category=categoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new DataNotFoundException("Category not found"));
        product.setCategory(category);
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setThumbnail(productDTO.getThumbnail());
        product.setName(productDTO.getName());
        productRepository.save(product);
        return toDto(        productRepository.save(product));
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public ProductImageDto createProductImage(Long productId, ProductImageDto productImageDTO)  {
        int quantity = productImageRepository.countByProductId(productId);
        if (quantity >= ProductImage.MAX_IMAGES_QUANTITY) {
            throw new DataIntegrityViolationException("Product can't have more than "+ProductImage.MAX_IMAGES_QUANTITY+" images");
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("Product with id "+productId+" not found"));
        ProductImage productImage = new ProductImage();
        productImage.setProduct(product);
        productImage.setImageUrl(productImageDTO.getImageUrl());
        productImage= productImageRepository.save(productImage);
        return new ProductImageDto(productImage.getProduct().getId(), productImage.getImageUrl());
    }
    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setThumbnail(product.getThumbnail());
        productDto.setName(product.getName());
        return productDto;
    }

    public Product toEntity(ProductDto productDto) {
        Product product = new Product();
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new DataIntegrityViolationException("Category not found"));
        product.setCategory(category);
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setThumbnail(productDto.getThumbnail());
        product.setName(productDto.getName());
        return product;
    }

}

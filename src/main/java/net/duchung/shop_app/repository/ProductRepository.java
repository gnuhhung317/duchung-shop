package net.duchung.shop_app.repository;

import net.duchung.shop_app.dto.ProductDto;
import net.duchung.shop_app.dto.ProductImageDto;
import net.duchung.shop_app.entity.Product;
import net.duchung.shop_app.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}

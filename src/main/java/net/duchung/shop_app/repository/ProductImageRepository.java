package net.duchung.shop_app.repository;

import net.duchung.shop_app.entity.Product;
import net.duchung.shop_app.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    Integer countByProductId(Long productId);
}

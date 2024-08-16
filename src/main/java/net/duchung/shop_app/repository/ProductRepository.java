package net.duchung.shop_app.repository;

import net.duchung.shop_app.dto.ProductDto;
import net.duchung.shop_app.dto.ProductImageDto;
import net.duchung.shop_app.entity.Product;
import net.duchung.shop_app.entity.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    @Query(value = "select * from products where " +
            " (:categoryId is null or :categoryId =-1 or category_id = :categoryId)" +
            " and ( :keyword is null or " +
            " lower(name) like lower(concat('%', :keyword, '%')) or" +
            " lower(description) like lower(concat('%', :keyword, '%')))", nativeQuery = true)
    Page<Product> searchProducts(@Param("keyword") String keyword,@Param("categoryId") Long categoryId, Pageable pageable);
}

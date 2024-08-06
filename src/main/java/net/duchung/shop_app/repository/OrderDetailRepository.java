package net.duchung.shop_app.repository;

import net.duchung.shop_app.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    public List<OrderDetail> findByOrderId(Long orderId);
}

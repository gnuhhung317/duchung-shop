package net.duchung.shop_app.service;

import net.duchung.shop_app.dto.OrderDetailDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    OrderDetailDto createOrderDetail(OrderDetailDto orderDetailDto);
    OrderDetailDto getOrderDetailById(Long id);
    OrderDetailDto updateOrderDetail(Long id, OrderDetailDto orderDetailDto);
    void deleteOrderDetail(Long id);
    List<OrderDetailDto> getOrdersDetailsByOrder(Long orderId);
}

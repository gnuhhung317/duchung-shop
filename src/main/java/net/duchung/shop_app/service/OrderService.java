package net.duchung.shop_app.service;

import net.duchung.shop_app.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);

    List<OrderDto> getOrdersByUser(Long userId);

//    List<OrderDto> getOrdersByUser(Long userId, Integer page, Integer size);

    OrderDto getOrderById(Long id);

    OrderDto updateOrder(Long id, OrderDto orderDto);

    void deleteOrder(Long id);

}

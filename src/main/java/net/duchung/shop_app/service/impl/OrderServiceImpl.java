package net.duchung.shop_app.service.impl;

import lombok.AllArgsConstructor;
import net.duchung.shop_app.dto.OrderDto;
import net.duchung.shop_app.entity.Order;
import net.duchung.shop_app.entity.OrderStatus;
import net.duchung.shop_app.entity.User;
import net.duchung.shop_app.exception.DataNotFoundException;
import net.duchung.shop_app.repository.OrderRepository;
import net.duchung.shop_app.repository.UserRepository;
import net.duchung.shop_app.service.OrderService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private UserRepository userRepository;
    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId()).orElseThrow(() -> new DataNotFoundException("User with id "+orderDto.getUserId()+"not found"));
        Order order = toEntity(orderDto);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setActive(true);
        if(order.getShippingDate()==null||order.getShippingDate().before(new Date())) {
            throw new IllegalArgumentException("Shipping date must be at least today");
        }
        return toDto(orderRepository.save(order));
    }

    @Override
    public List<OrderDto> getOrdersByUser(Long userId) {

        return orderRepository.findByUserId(userId).stream().map(this::toDto).toList();
    }

    @Override
    public OrderDto getOrderById(Long id) {
        return toDto(orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order with id "+id+" not found")));
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order with id "+id+" not found"));

        order.setFullName(orderDto.getFullName());
        order.setAddress(orderDto.getAddress());
        order.setPhoneNumber(orderDto.getPhoneNumber());
        order.setEmail(orderDto.getEmail());
        order.setNote(orderDto.getNote());
        order.setTotalMoney(orderDto.getTotalMoney());
        order.setShippingDate(orderDto.getShippingDate());
        order.setShippingMethod(orderDto.getShippingMethod());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        return toDto(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Long id) {

        Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order with id "+id+" not found"));
        order.setActive(false);
    }

    @Override
    public void activeOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new DataNotFoundException("Order with id "+id+" not found"));
        order.setActive(true);
    }

    public OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setFullName(order.getFullName());
        orderDto.setAddress(order.getAddress());
        orderDto.setPhoneNumber(order.getPhoneNumber());
        orderDto.setEmail(order.getEmail());
        orderDto.setNote(order.getNote());
        orderDto.setTotalMoney(order.getTotalMoney());
        orderDto.setShippingMethod(order.getShippingMethod());
        orderDto.setShippingAddress(order.getShippingAddress());
        orderDto.setPaymentMethod(order.getPaymentMethod());

        return orderDto;
    }
    public Order toEntity(OrderDto orderDto) {
        Order order = new Order();

        order.setFullName(orderDto.getFullName());
        order.setAddress(orderDto.getAddress());
        order.setPhoneNumber(orderDto.getPhoneNumber());
        order.setEmail(orderDto.getEmail());
        order.setNote(orderDto.getNote());
        order.setTotalMoney(orderDto.getTotalMoney());
        order.setShippingDate(orderDto.getShippingDate());
        order.setShippingMethod(orderDto.getShippingMethod());
        order.setShippingAddress(orderDto.getShippingAddress());
        order.setPaymentMethod(orderDto.getPaymentMethod());
        return order;
    }
}

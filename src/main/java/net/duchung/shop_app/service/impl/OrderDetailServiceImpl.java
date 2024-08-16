package net.duchung.shop_app.service.impl;

import net.duchung.shop_app.dto.OrderDetailDto;
import net.duchung.shop_app.entity.Order;
import net.duchung.shop_app.entity.OrderDetail;
import net.duchung.shop_app.entity.Product;
import net.duchung.shop_app.exception.DataNotFoundException;
import net.duchung.shop_app.repository.OrderDetailRepository;
import net.duchung.shop_app.repository.OrderRepository;
import net.duchung.shop_app.repository.ProductRepository;
import net.duchung.shop_app.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    @Transactional
    public OrderDetailDto createOrderDetail(OrderDetailDto orderDetailDto) {
        Order order = orderRepository.findById(orderDetailDto.getOrderId()).orElseThrow(() -> new DataNotFoundException("Order with id "+orderDetailDto.getOrderId()+" not found"));

        Product product = productRepository.findById(orderDetailDto.getProductId()).orElseThrow(() -> new DataNotFoundException("Product with id "+orderDetailDto.getProductId()+" not found"));
        OrderDetail orderDetail = toEntity(orderDetailDto,order,product);
        return toDto(orderDetailRepository.save(orderDetail));
    }

    @Override
    public OrderDetailDto getOrderDetailById(Long id) {
        return toDto(orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("OrderDetail with id "+id+" not found")));
    }

    @Override
    @Transactional
    public OrderDetailDto updateOrderDetail(Long id, OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("OrderDetail with id "+id+" not found"));
        Order order = orderRepository.findById(orderDetailDto.getOrderId()).orElseThrow(() -> new DataNotFoundException("Order with id "+ id+" not found"));
        Product product = productRepository.findById(orderDetailDto.getProductId()).orElseThrow(() -> new DataNotFoundException("Order with id "+id+" not found"));
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setNumberOfProducts(orderDetailDto.getNumberOfProducts());
        orderDetail.setPrice(orderDetailDto.getPrice());
        orderDetail.setTotalMoney(orderDetailDto.getTotalMoney());
        orderDetail.setColor(orderDetailDto.getColor());
        return toDto(orderDetailRepository.save(orderDetail));
    }

    @Override
    @Transactional
    public void deleteOrderDetail(Long id) {

        OrderDetail orderDetail = orderDetailRepository.findById(id).orElseThrow(() -> new DataNotFoundException("OrderDetail with id "+id+" not found"));
        orderDetailRepository.delete(orderDetail);
    }

    @Override
    public List<OrderDetailDto> getOrdersDetailsByOrder(Long orderId) {

        return orderDetailRepository.findByOrderId(orderId).stream().map(this::toDto).toList();
    }
    public OrderDetailDto toDto(OrderDetail orderDetail) {
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        orderDetailDto.setOrderId(orderDetail.getOrder().getId());
        orderDetailDto.setProductId(orderDetail.getProduct().getId());
        orderDetailDto.setNumberOfProducts(orderDetail.getNumberOfProducts());
        orderDetailDto.setPrice(orderDetail.getPrice());
        orderDetailDto.setTotalMoney(orderDetail.getTotalMoney());
        orderDetailDto.setColor(orderDetail.getColor());
        return orderDetailDto;
    }
    public OrderDetail toEntity(OrderDetailDto orderDetailDto,Order order,Product product) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setNumberOfProducts(orderDetailDto.getNumberOfProducts());
        orderDetail.setPrice(orderDetailDto.getPrice());
        orderDetail.setTotalMoney(orderDetailDto.getTotalMoney());
        orderDetail.setColor(orderDetailDto.getColor());
        return orderDetail;
    }
}

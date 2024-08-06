package net.duchung.shop_app.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.duchung.shop_app.dto.OrderDetailDto;
import net.duchung.shop_app.entity.OrderDetail;
import net.duchung.shop_app.service.OrderDetailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderDetails")
@AllArgsConstructor
public class OrderDetailController {
    private OrderDetailService orderDetailService;
    @PostMapping("")
    public ResponseEntity<OrderDetailDto> createOrderDetail(OrderDetailDto orderDetailDto) {
        return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetailDto));

    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDto> getOrderDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(orderDetailService.getOrderDetailById(id));
    }
    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDetailDto>> getOrderDetails(@PathVariable Long id) {
        return ResponseEntity.ok(orderDetailService.getOrdersDetailsByOrder(id));
    }
    @PutMapping("{id}")
    public ResponseEntity<OrderDetailDto> updateOrderDetail(@PathVariable Long id, @Valid @RequestBody OrderDetailDto orderDetailDto) {
        return ResponseEntity.ok(orderDetailService.updateOrderDetail(id, orderDetailDto));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok().build();
    }
}

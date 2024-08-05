package net.duchung.shop_app.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.duchung.shop_app.dto.OrderDto;
import net.duchung.shop_app.service.OrderService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@AllArgsConstructor
public class OrderController {
    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

//    @GetMapping("")
//    public ResponseEntity<?> getAllOrders(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int limit) {
//    }

    @GetMapping("/user")
    public ResponseEntity<?> getOrders(@Valid @RequestParam Long id) {
        return ResponseEntity.ok(orderService.getOrdersByUser(id));
    }
    @GetMapping("")
    public ResponseEntity<?> getOrderById(@RequestParam Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok(orderService.updateOrder(id, orderDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.ok().build();
    }


}
